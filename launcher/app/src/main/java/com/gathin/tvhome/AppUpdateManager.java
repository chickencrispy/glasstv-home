package com.gathin.tvhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Manages OTA in-app update checks, changelog display, and seamless APK downloading
 * for Glass TV Home directly on Android TV.
 */
public final class AppUpdateManager {
    private static final String TAG = "AppUpdateManager";
    private static final String VERSION_URL = "https://raw.githubusercontent.com/chickencrispy/glasstv-home/main/release/version.json";
    private static final String PREFS_NAME = "update_prefs";
    private static final String KEY_LAST_CHECK = "last_check_timestamp";
    private static final String KEY_LAST_SCHEDULED = "last_scheduled_check";
    public static final String KEY_AUTO_UPDATE = "pref_auto_update";
    public static final String KEY_AUTO_UPDATE_TIME = "pref_auto_update_time";
    public static final String DEFAULT_AUTO_UPDATE_TIME = "03:30 AM";

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private AppUpdateManager() {
    }

    public static boolean isAutoUpdateEnabled(Context context) {
        if (context == null) return true;
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_AUTO_UPDATE, true); // default ON
    }

    public static void setAutoUpdateEnabled(Context context, boolean enabled) {
        if (context == null) return;
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_AUTO_UPDATE, enabled).apply();
    }

    public static String getAutoUpdateTime(Context context) {
        if (context == null) return DEFAULT_AUTO_UPDATE_TIME;
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sp.getString(KEY_AUTO_UPDATE_TIME, DEFAULT_AUTO_UPDATE_TIME);
    }

    public static void setAutoUpdateTime(Context context, String time) {
        if (context == null) return;
        SharedPreferences sp = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_AUTO_UPDATE_TIME, time != null ? time : DEFAULT_AUTO_UPDATE_TIME).apply();
    }

    public static void checkAutoUpdateIfDue(Activity activity) {
        if (activity == null || !isAutoUpdateEnabled(activity)) return;
        SharedPreferences sp = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long lastCheck = sp.getLong(KEY_LAST_CHECK, 0);
        // Check once every 12 hours at least
        if (System.currentTimeMillis() - lastCheck >= 12 * 3600 * 1000L) {
            checkForUpdates(activity, false);
        }
    }

    /**
     * Runs the automatic check at the time chosen in Settings, once a day. Called from Home's minute
     * tick, so it needs no alarm permission and no background service: Home is in front when it runs.
     * A missed time (TV off) is caught up the next time Home is in front.
     */
    public static void checkScheduledAutoUpdate(Activity activity) {
        if (activity == null || activity.isFinishing() || !isAutoUpdateEnabled(activity)) return;

        int[] clock = parseClock(getAutoUpdateTime(activity));
        Calendar now = Calendar.getInstance();
        Calendar due = (Calendar) now.clone();
        due.set(Calendar.HOUR_OF_DAY, clock[0]);
        due.set(Calendar.MINUTE, clock[1]);
        due.set(Calendar.SECOND, 0);
        due.set(Calendar.MILLISECOND, 0);
        if (now.before(due)) return;

        SharedPreferences sp = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (sp.getLong(KEY_LAST_SCHEDULED, 0) >= due.getTimeInMillis()) return;
        sp.edit().putLong(KEY_LAST_SCHEDULED, now.getTimeInMillis()).apply();
        checkForUpdates(activity, false);
    }

    /** Parses "03:30 AM" / "12:00 PM" into {hour24, minute}; falls back to 03:30. */
    private static int[] parseClock(String value) {
        try {
            String[] parts = value.trim().split("\\s+");
            String[] hm = parts[0].split(":");
            int hour = Integer.parseInt(hm[0]);
            int minute = Integer.parseInt(hm[1]);
            boolean pm = parts.length > 1 && "PM".equalsIgnoreCase(parts[1]);
            if (pm && hour < 12) hour += 12;
            if (!pm && hour == 12) hour = 0;
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) throw new IllegalArgumentException();
            return new int[]{hour, minute};
        } catch (RuntimeException e) {
            return new int[]{3, 30};
        }
    }

    public interface CheckCallback {
        void onResult(boolean hasUpdate, String newVersionName, String changelog, String apkUrl);
    }

    /**
     * Checks for updates. If manualCheck is true, shows feedback toasts even when up-to-date.
     */
    public static void checkForUpdates(Activity activity, boolean manualCheck) {
        if (activity == null || activity.isFinishing()) return;

        if (manualCheck) {
            Toast.makeText(activity, "🔄 Memeriksa pembaruan terbaru...", Toast.LENGTH_SHORT).show();
        }

        executor.execute(() -> {
            try {
                URL url = new URL(VERSION_URL + "?t=" + System.currentTimeMillis());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(6000);
                conn.setReadTimeout(6000);
                conn.setUseCaches(false);

                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    if (manualCheck) {
                        mainHandler.post(() -> Toast.makeText(activity, "❌ Gagal memeriksa server pembaruan (HTTP " + responseCode + ")", Toast.LENGTH_SHORT).show());
                    }
                    return;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                conn.disconnect();

                JSONObject json = new JSONObject(sb.toString());
                int remoteVersionCode = json.optInt("versionCode", 0);
                String remoteVersionName = json.optString("versionName", "");
                String apkUrl = json.optString("apkUrl", "");
                String changelog = json.optString("changelog", "Peningkatan performa dan stabilitas.");

                PackageManager pm = activity.getPackageManager();
                PackageInfo pInfo = pm.getPackageInfo(activity.getPackageName(), 0);
                int currentVersionCode = pInfo.versionCode;
                String currentVersionName = pInfo.versionName;

                // Save timestamp
                SharedPreferences sp = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                sp.edit().putLong(KEY_LAST_CHECK, System.currentTimeMillis()).apply();

                mainHandler.post(() -> {
                    if (activity.isFinishing()) return;

                    if (remoteVersionCode > currentVersionCode) {
                        showUpdateDialog(activity, remoteVersionName, currentVersionName, changelog, apkUrl);
                    } else {
                        if (manualCheck) {
                            Toast.makeText(activity, "✅ Glass TV Home sudah versi terbaru (v" + currentVersionName + ")!", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, "Error checking updates: " + e.getMessage());
                if (manualCheck) {
                    mainHandler.post(() -> Toast.makeText(activity, "❌ Gagal memeriksa pembaruan. Pastikan Wi-Fi terhubung.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private static void showUpdateDialog(Activity activity, String newVersionName, String currentVersionName, String changelog, String apkUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.Theme_TvHome_GlassDialog);
        builder.setTitle("🚀 Pembaruan Baru Tersedia (v" + newVersionName + ")");
        
        String message = "Versi Saat Ini: v" + currentVersionName + "\n"
                + "Versi Terbaru: v" + newVersionName + "\n\n"
                + "📋 Catatan Pembaruan:\n" + changelog + "\n\n"
                + "Apakah Anda ingin mengunduh dan memasangnya sekarang?";
        builder.setMessage(message);

        builder.setPositiveButton("Unduh & Pasang", (dialog, which) -> {
            dialog.dismiss();
            downloadAndInstallApk(activity, apkUrl, newVersionName);
        });

        builder.setNegativeButton("Nanti Saja", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @SuppressWarnings("deprecation")
    private static void downloadAndInstallApk(Activity activity, String apkUrl, String versionName) {
        // Check install permission on Android 8.0+ (Oreo, API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!activity.getPackageManager().canRequestPackageInstalls()) {
                AlertDialog.Builder permDialog = new AlertDialog.Builder(activity, R.style.Theme_TvHome_GlassDialog);
                permDialog.setTitle("⚠️ Izin Pemasangan Diperlukan");
                permDialog.setMessage("Aktifkan 'Allow from this source' untuk mengizinkan Glass TV Home memasang pembaruan otomatis.");
                permDialog.setPositiveButton("Buka Pengaturan", (d, w) -> {
                    try {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                                Uri.parse("package:" + activity.getPackageName()));
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(activity, "Buka Pengaturan > Keamanan > Install Unknown Apps", Toast.LENGTH_LONG).show();
                    }
                });
                permDialog.setNegativeButton("Batal", (d, w) -> d.dismiss());
                permDialog.show();
                return;
            }
        }

        ProgressDialog progressDialog = new ProgressDialog(activity, R.style.Theme_TvHome_GlassDialog);
        progressDialog.setTitle("📥 Mengunduh Pembaruan (v" + versionName + ")");
        progressDialog.setMessage("Sedang mengunduh file pemasangan...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();

        executor.execute(() -> {
            File cacheDir = activity.getExternalCacheDir() != null ? activity.getExternalCacheDir() : activity.getCacheDir();
            File apkFile = new File(cacheDir, "glass-tv-update.apk");

            InputStream is = null;
            FileOutputStream fos = null;
            HttpURLConnection conn = null;

            try {
                URL url = new URL(apkUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(15000);
                conn.connect();

                int fileLength = conn.getContentLength();
                is = conn.getInputStream();
                fos = new FileOutputStream(apkFile);

                byte[] buffer = new byte[8192];
                long total = 0;
                int count;

                while ((count = is.read(buffer)) != -1) {
                    total += count;
                    fos.write(buffer, 0, count);
                    if (fileLength > 0) {
                        int progress = (int) (total * 100 / fileLength);
                        mainHandler.post(() -> progressDialog.setProgress(progress));
                    }
                }
                fos.flush();

                mainHandler.post(() -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    installApk(activity, apkFile);
                });

            } catch (Exception e) {
                Log.e(TAG, "Download error: " + e.getMessage());
                mainHandler.post(() -> {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(activity, "❌ Gagal mengunduh pembaruan: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            } finally {
                try {
                    if (fos != null) fos.close();
                    if (is != null) is.close();
                    if (conn != null) conn.disconnect();
                } catch (Exception ignored) {
                }
            }
        });
    }

    private static void installApk(Activity activity, File apkFile) {
        if (!apkFile.exists()) {
            Toast.makeText(activity, "File APK pembaruan tidak ditemukan.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", apkFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Install error: " + e.getMessage());
            Toast.makeText(activity, "❌ Gagal memulai pemasangan: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
