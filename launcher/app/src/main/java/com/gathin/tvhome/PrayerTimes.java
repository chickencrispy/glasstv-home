package com.gathin.tvhome;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Waktu solat from JAKIM's timetable, for the zone this location falls in: cached locally per zone and
 * month, with a solar-calculation fallback so it never fails offline.
 */
final class PrayerTimes {
    private static final String TAG = "TvHome.Prayer";
    private static final String PREFS_NAME = "prayer_times_cache";
    private static final String KEY_JSON = "takwim_wly01";
    private static final String KEY_MONTH = "takwim_month";

    /** UTC+8, the whole of Malaysia. */
    private static final double TIMEZONE = 8.0;

    private static final String[] PRAYER_NAMES = {"Subuh", "Syuruk", "Zohor", "Asar", "Maghrib", "Isyak"};
    private static final String[] PRAYER_KEYS = {"fajr", "syuruk", "dhuhr", "asr", "maghrib", "isha"};

    /**
     * Jam maghrib hari ini dalam milidetik, atau -1 kalau takwim lokasi ini belum ada.
     *
     * Dipakai layar utama sebagai batas ganti hari Hijriah: hari Hijriah mulai saat maghrib, bukan
     * tengah malam. Sengaja tanpa hitungan cadangan — kalau takwimnya belum ada, lebih baik tanggal
     * Hijriah tidak ditampilkan daripada menebak batas harinya.
     */
    static long maghribToday(Context context) {
        long[] times = getTimesForToday(context, Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        if (times == null || times.length < 6) return -1L;
        return times[4];
    }

    private PrayerTimes() {
    }

    /** Syncs the timetable for the active location/zone in the background, then runs {@code onUpdated}. */
    static void sync(Context context, Runnable onUpdated) {
        new Thread(() -> {
            try {
                SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                Calendar now = Calendar.getInstance();
                int currentMonth = now.get(Calendar.YEAR) * 100 + (now.get(Calendar.MONTH) + 1);
                String zone = getJakimZone(context);

                URL url = new URL("https://api.waktusolat.app/v2/solat/" + zone);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(8000);
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == 200) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                    reader.close();

                    prefs.edit()
                            .putString(KEY_JSON, sb.toString())
                            .putInt(KEY_MONTH, currentMonth)
                            .putString("cached_zone", zone)
                            .apply();

                    if (onUpdated != null) {
                        new Handler(Looper.getMainLooper()).post(onUpdated);
                    }
                    Log.i(TAG, "Synced prayer times for zone " + zone + " successfully");
                }
            } catch (Exception e) {
                Log.w(TAG, "Prayer sync offline/failed: " + e.getMessage());
            }
        }, "prayer-sync").start();
    }

    /** JAKIM zone code to the name that zone is known by, for the zone name in the prayer line. */
    private static final Map<String, String> ZONE_NAMES = new HashMap<>();

    static {
        // W.P. & Putrajaya
        ZONE_NAMES.put("WLY01", "Putrajaya & Kuala Lumpur");
        ZONE_NAMES.put("WLY02", "Labuan");

        // Selangor
        ZONE_NAMES.put("SGR01", "Shah Alam, Petaling, Gombak, Sepang");
        ZONE_NAMES.put("SGR02", "Kuala Selangor, Sabak Bernam");
        ZONE_NAMES.put("SGR03", "Klang, Kuala Langat, Banting");

        // Melaka
        ZONE_NAMES.put("MLK01", "Bandaraya Melaka, Alor Gajah, Jasin");

        // Negeri Sembilan
        ZONE_NAMES.put("NGS02", "Seremban, Port Dickson, Nilai");
        ZONE_NAMES.put("NGS01", "Tampin, Jempol");

        // Johor
        ZONE_NAMES.put("JHR02", "Johor Bahru, Pasir Gudang, Kulai");
        ZONE_NAMES.put("JHR03", "Batu Pahat, Muar, Tangkak, Segamat");
        ZONE_NAMES.put("JHR04", "Kluang, Mersing");

        // Pulau Pinang
        ZONE_NAMES.put("PNG01", "Seluruh Negeri Pulau Pinang");

        // Perak
        ZONE_NAMES.put("PRK02", "Ipoh, Batu Gajah, Kampar");
        ZONE_NAMES.put("PRK03", "Taiping, Bagan Serai, Parit Buntar");
        ZONE_NAMES.put("PRK04", "Lumut, Manjung, Pangkor, Sitiawan");
        ZONE_NAMES.put("PRK01", "Tapah, Slim River, Tanjung Malim");

        // Kedah & Perlis
        ZONE_NAMES.put("KDH01", "Kota Setar, Pokok Sena, Kubang Pasu");
        ZONE_NAMES.put("KDH02", "Kuala Muda, Yan, Pendang, Sungai Petani");
        ZONE_NAMES.put("KDH07", "Pulau Langkawi");
        ZONE_NAMES.put("PLS01", "Kangar, Padang Besar, Arau");

        // Pahang
        ZONE_NAMES.put("PHG02", "Kuantan, Pekan, Muadzam Shah");
        ZONE_NAMES.put("PHG03", "Jerantut, Temerloh, Maran, Bera");
        ZONE_NAMES.put("PHG04", "Bentong, Raub, Lipis");
        ZONE_NAMES.put("PHG06", "Cameron Highlands");

        // Terengganu & Kelantan
        ZONE_NAMES.put("TRG01", "Kuala Terengganu, Marang, Kuala Nerus");
        ZONE_NAMES.put("TRG04", "Kemaman, Dungun");
        ZONE_NAMES.put("KTN01", "Kota Bharu, Bachok, Pasir Puteh, Tumpat");
        ZONE_NAMES.put("KTN02", "Machang, Pasir Mas, Tanah Merah");

        // Sabah
        ZONE_NAMES.put("SBH07", "Kota Kinabalu, Penampang, Tuaran, Papar");
        ZONE_NAMES.put("SBH03", "Tawau, Balung, Merotai, Kalabakan");
        ZONE_NAMES.put("SBH05", "Kudat, Kota Marudu, Pitas, Sandakan");

        // Sarawak
        ZONE_NAMES.put("SWK08", "Kuching, Bau, Lundu, Samarahan, Serian");
        ZONE_NAMES.put("SWK04", "Bintulu, Tatau, Sebauh");
        ZONE_NAMES.put("SWK02", "Miri, Niah, Bekenu, Sibuti");
        ZONE_NAMES.put("SWK05", "Sibu, Mukah, Kanowit, Song");
    }

    public static String getActiveZone(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.contains("manual_prayer_zone")) {
            return prefs.getString("manual_prayer_zone", "WLY01");
        }
        return getJakimZone(context);
    }

    public static String getActiveZoneName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.contains("manual_prayer_zone_name")) {
            return prefs.getString("manual_prayer_zone_name", "Putrajaya & Kuala Lumpur");
        }
        String zone = getActiveZone(context).toUpperCase(Locale.ROOT);
        String name = ZONE_NAMES.get(zone);
        return name != null ? name : AppLocationManager.getLocationName(context);
    }

    public static String getShortZoneName(Context context) {
        String name = getActiveZoneName(context);
        if (name == null || name.trim().isEmpty()) return "Putrajaya";
        int cut = name.indexOf(',');
        if (cut > 0) name = name.substring(0, cut);
        cut = name.indexOf('&');
        if (cut > 0) name = name.substring(0, cut);
        cut = name.indexOf('(');
        if (cut > 0) name = name.substring(0, cut);
        name = name.trim();
        return name.isEmpty() ? "Putrajaya" : name;
    }

    public static void clearManualZone(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .remove("manual_prayer_zone")
                .remove("manual_prayer_zone_name")
                .apply();
    }

    public static void setZone(Context context, String zoneCode) {
        if (zoneCode == null) return;
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putString("manual_prayer_zone", zoneCode)
                .putString("manual_prayer_zone_name", zoneCode)
                .apply();
        sync(context, null);
    }

    private static String getJakimZone(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.contains("manual_prayer_zone")) {
            return prefs.getString("manual_prayer_zone", "WLY01");
        }
        String loc = AppLocationManager.getLocationName(context).toLowerCase(Locale.ROOT);
        String stationId = AppLocationManager.getStationId(context).toUpperCase(Locale.ROOT);

        if (loc.contains("putrajaya") || stationId.equals("CA17W")) return "WLY01";
        if (loc.contains("kuala lumpur") || loc.contains("cheras") || loc.contains("batu muda")) return "WLY01";
        if (loc.contains("labuan")) return "WLY02";
        if (loc.contains("melaka") || loc.contains("alor gajah") || loc.contains("rambai")) return "MLK01";
        if (loc.contains("petaling") || loc.contains("shah alam")) return "SGR01";
        if (loc.contains("klang") || loc.contains("banting")) return "SGR03";
        if (loc.contains("seremban") || loc.contains("nilai") || loc.contains("port dickson")) return "NGS02";
        if (loc.contains("johor") || loc.contains("larkin") || loc.contains("pasir gudang")) return "JHR02";
        if (loc.contains("batu pahat")) return "JHR03";
        if (loc.contains("kluang")) return "JHR04";
        if (loc.contains("tangkak")) return "JHR01";
        if (loc.contains("pinang") || loc.contains("seberang") || loc.contains("minden")) return "PNG01";
        if (loc.contains("ipoh") || loc.contains("tasek") || loc.contains("taiping")) return "PRK02";
        if (loc.contains("manjung")) return "PRK01";
        if (loc.contains("alor setar")) return "KDH01";
        if (loc.contains("sungai petani")) return "KDH02";
        if (loc.contains("langkawi")) return "KDH07";
        if (loc.contains("perlis") || loc.contains("kangar")) return "PLS01";
        if (loc.contains("kuantan")) return "PHG02";
        if (loc.contains("temerloh")) return "PHG04";
        if (loc.contains("terengganu") || loc.contains("kemaman")) return "TRG01";
        if (loc.contains("kota bharu") || loc.contains("kelantan")) return "KTN01";
        if (loc.contains("kota kinabalu")) return "SBH07";
        if (loc.contains("sandakan")) return "SBH05";
        if (loc.contains("tawau")) return "SBH03";
        if (loc.contains("kuching")) return "SWK08";
        if (loc.contains("miri")) return "SWK04";
        if (loc.contains("bintulu")) return "SWK06";
        if (loc.contains("sibu")) return "SWK05";

        return "WLY01"; // Default Putrajaya
    }

    /** Returns formatted clean UI CharSequence with highlighted next prayer, bold and legible. */
    static CharSequence getFormatted(Context context) {
        Calendar now = Calendar.getInstance();
        int day = now.get(Calendar.DAY_OF_MONTH);
        long[] times = getTimesForToday(context, day);

        if (times == null || times.length < 6) {
            times = calculateFallback(context, now);
        }

        long nowMs = now.getTimeInMillis();
        int nextIdx = -1;

        // Find next prayer (skip Syuruk idx 1)
        for (int i = 0; i < times.length; i++) {
            if (i == 1) continue;
            if (times[i] > nowMs) {
                nextIdx = i;
                break;
            }
        }
        if (nextIdx == -1) nextIdx = 0; // After Isyak -> Subuh next morning

        SimpleDateFormat timeFmt = new SimpleDateFormat("HH:mm", Locale.getDefault());

        SpannableStringBuilder sb = new SpannableStringBuilder();

        // LINE 1: LOCATION  •  SETERUSNYA: ASAR 16:18
        String nextName = PRAYER_NAMES[nextIdx].toUpperCase(Locale.ROOT);
        long displayTimeMs = times[nextIdx];
        if (nextIdx == 0 && times[0] < nowMs) {
            Calendar tomorrow = (Calendar) now.clone();
            tomorrow.add(Calendar.DAY_OF_MONTH, 1);
            long[] tomTimes = getTimesForToday(context, tomorrow.get(Calendar.DAY_OF_MONTH));
            if (tomTimes == null || tomTimes.length < 6) tomTimes = calculateFallback(context, tomorrow);
            if (tomTimes != null && tomTimes.length > 0) displayTimeMs = tomTimes[0];
        }
        String nextTime = timeFmt.format(new Date(displayTimeMs));

        int l1Start = sb.length();
        String zoneHeader = getShortZoneName(context).toUpperCase(Locale.ROOT);
        sb.append(zoneHeader).append("  •  SETERUSNYA: ").append(nextName).append(" ").append(nextTime);
        int l1End = sb.length();
        sb.setSpan(new ForegroundColorSpan(0xFFFFFFFF), l1Start, l1End, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new StyleSpan(Typeface.BOLD), l1Start, l1End, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sb.append("\n");

        // LINE 2: SUBUH 05:49  •  SYURUK 06:55  •  ZOHOR 13:01  •  ASAR 16:18  •  MAGHRIB 19:00  •  ISYAK 20:10
        int[] displayIndices = {0, 1, 2, 3, 4, 5}; // Subuh, Syuruk, Zohor, Asar, Maghrib, Isyak
        for (int i = 0; i < displayIndices.length; i++) {
            int idx = displayIndices[i];
            boolean isNext = (idx == nextIdx);
            int start = sb.length();

            String name = PRAYER_NAMES[idx].toUpperCase(Locale.ROOT);
            String timeStr = timeFmt.format(new Date(times[idx]));

            sb.append(name).append(" ").append(timeStr);
            if (isNext) {
                sb.setSpan(new ForegroundColorSpan(0xFF4ADE80), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(Typeface.BOLD), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                sb.setSpan(new ForegroundColorSpan(0xE6FFFFFF), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(Typeface.BOLD), start, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (i < displayIndices.length - 1) {
                int dotStart = sb.length();
                sb.append("  •  ");
                sb.setSpan(new ForegroundColorSpan(0x80FFFFFF), dotStart, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        return sb;
    }

    private static long[] getTimesForToday(Context context, int day) {
        try {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            String jsonStr = prefs.getString(KEY_JSON, null);
            if (jsonStr == null) return null;

            JSONObject obj = new JSONObject(jsonStr);
            JSONArray prayers = obj.getJSONArray("prayers");
            for (int i = 0; i < prayers.length(); i++) {
                JSONObject p = prayers.getJSONObject(i);
                if (p.optInt("day") == day) {
                    long[] res = new long[6];
                    for (int k = 0; k < 6; k++) {
                        res[k] = p.getLong(PRAYER_KEYS[k]) * 1000L;
                    }
                    return res;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /** Astronomical calculation fallback based on user's active Malaysian location. */
    private static long[] calculateFallback(Context context, Calendar cal) {
        double latitude = AppLocationManager.getLatitude(context);
        double longitude = AppLocationManager.getLongitude(context);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        double d = 367.0 * year - Math.floor((7.0 * (year + Math.floor((month + 9.0) / 12.0))) / 4.0)
                + Math.floor((275.0 * month) / 9.0) + day - 730531.5;

        double L = (280.461 + 0.9856474 * d) % 360.0;
        if (L < 0) L += 360.0;
        double M = (357.528 + 0.9856003 * d) % 360.0;
        if (M < 0) M += 360.0;

        double lambda = L + 1.915 * Math.sin(Math.toRadians(M)) + 0.020 * Math.sin(Math.toRadians(2 * M));
        double epsilon = 23.439 - 0.0000004 * d;
        double alpha = Math.toDegrees(Math.atan2(Math.cos(Math.toRadians(epsilon)) * Math.sin(Math.toRadians(lambda)), Math.cos(Math.toRadians(lambda)))) / 15.0;
        double delta = Math.toDegrees(Math.asin(Math.sin(Math.toRadians(epsilon)) * Math.sin(Math.toRadians(lambda))));

        double noon = 12.0 + TIMEZONE - (longitude / 15.0) - (L / 15.0 - alpha);
        if (noon < 0) noon += 24.0;
        if (noon >= 24) noon -= 24.0;

        double fajrHour = noon - sunAngleHour(-20.0, latitude, delta);
        double sunriseHour = noon - sunAngleHour(-0.833, latitude, delta);
        double asrHour = noon + asrHour(1, latitude, delta);
        double sunsetHour = noon + sunAngleHour(-0.833, latitude, delta);
        double ishaHour = noon + sunAngleHour(-18.0, latitude, delta);

        // Malaysia standard rounding & +2 min ihtiyat
        double[] hours = {fajrHour + 0.05, sunriseHour, noon + 0.04, asrHour + 0.04, sunsetHour + 0.05, ishaHour + 0.05};
        long[] res = new long[6];
        Calendar base = (Calendar) cal.clone();
        base.set(Calendar.SECOND, 0);
        base.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < 6; i++) {
            int h = (int) hours[i];
            int m = (int) ((hours[i] - h) * 60.0);
            base.set(Calendar.HOUR_OF_DAY, h);
            base.set(Calendar.MINUTE, m);
            res[i] = base.getTimeInMillis();
        }
        return res;
    }

    private static double sunAngleHour(double angle, double lat, double delta) {
        double cosH = (Math.sin(Math.toRadians(angle)) - Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(delta)))
                / (Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(delta)));
        cosH = Math.max(-1.0, Math.min(1.0, cosH));
        return Math.toDegrees(Math.acos(cosH)) / 15.0;
    }

    private static double asrHour(int shadowFactor, double lat, double delta) {
        double phi = Math.abs(lat - delta);
        double cotA = shadowFactor + Math.tan(Math.toRadians(phi));
        double angle = Math.toDegrees(Math.atan(1.0 / cotA));
        return sunAngleHour(angle, lat, delta);
    }
}
