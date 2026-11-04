package com.gathin.tvhome;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import java.time.LocalDate;

/**
 * Baris tanggal yang bergantian antara tanggal Masehi dan tanggal Hijriah: Masehi 10 detik, Hijriah
 * 5 detik, dengan animasi geser tiap pergantian. Dipakai Home dan screensaver, supaya keduanya
 * menampilkan tanggal Hijriah yang sama, dari tabel dan batas maghrib yang sama.
 *
 * Tanpa tanggal Hijriah (fiturnya dimatikan, jadwal solat lokasi ini belum ada, atau harinya di luar
 * tabel) barisnya diam di tanggal Masehi seperti semula.
 *
 * Dua TextView-nya ditumpuk dalam satu FrameLayout dan keduanya tetap terlihat, yang tidak giliran
 * hanya tembus pandang: lebarnya jadi lebar tanggal yang paling panjang, sehingga blok tanggal tidak
 * bergeser saat bertukar.
 */
final class DateAlternator {

    /** Lama tanggal Masehi tampil sebelum bertukar ke tanggal Hijriah. */
    static final long MASEHI_MS = 10_000L;
    /** Lama tanggal Hijriah tampil sebelum kembali ke tanggal Masehi. */
    static final long HIJRI_MS = 5_000L;
    /** Animasi geser saat bertukar, jarak geser, dan fade pendeknya untuk reduced_motion. */
    private static final long SLIDE_MS = 300L;
    private static final long FADE_MS = 120L;
    private static final float SLIDE_DP = 6f;

    private final Handler handler;
    private final TextView masehiView;
    private final TextView hijriView;
    private final float density;
    private boolean reducedMotion;
    private boolean showingHijri;
    private boolean running;

    DateAlternator(Handler handler, TextView masehiView, TextView hijriView, float density) {
        this.handler = handler;
        this.masehiView = masehiView;
        this.hijriView = hijriView;
        this.density = density;
    }

    /** Home dan screensaver membaca setelan yang sama (config.xml, reduced_motion). */
    void setReducedMotion(boolean reducedMotion) {
        this.reducedMotion = reducedMotion;
    }

    private final Runnable swap = new Runnable() {
        @Override
        public void run() {
            boolean hijri = !showingHijri;
            show(hijri, true);
            handler.postDelayed(this, hijri ? HIJRI_MS : MASEHI_MS);
        }
    };

    /**
     * Menaruh tanggal Hijriah hari ini di baris itu lalu memulai pertukarannya; {@code hijriText} null
     * menghentikan pertukaran dan meninggalkan tanggal Masehi seperti semula. Aman dipanggil berulang
     * tiap menit: teksnya diperbarui (tanggal Hijriah berganti di maghrib), tapi timer yang sedang
     * berjalan tidak direset.
     */
    void refresh(String hijriText) {
        if (masehiView == null || hijriView == null) return;
        hijriView.setText(hijriText != null ? hijriText : "");
        if (hijriText == null) {
            stop();
            show(false, false);
            return;
        }
        if (!running) {
            running = true;
            show(false, false);
            handler.postDelayed(swap, MASEHI_MS);
        }
    }

    /** Melepas timernya; dipanggil begitu Home atau screensaver tidak di depan lagi. */
    void stop() {
        handler.removeCallbacks(swap);
        running = false;
    }

    /** Menampilkan salah satu tanggal; yang tidak giliran tetap terlihat supaya lebar blok tidak berubah. */
    private void show(boolean hijri, boolean animate) {
        TextView in = hijri ? hijriView : masehiView;
        TextView out = hijri ? masehiView : hijriView;
        showingHijri = hijri;
        in.animate().cancel();
        out.animate().cancel();
        if (!animate) {
            in.setAlpha(1f);
            in.setTranslationY(0f);
            out.setAlpha(0f);
            out.setTranslationY(0f);
            return;
        }
        float shift = SLIDE_DP * density;
        long duration = reducedMotion ? FADE_MS : SLIDE_MS;
        in.setTranslationY(shift);
        in.setAlpha(0f);
        in.animate().translationY(0f).alpha(1f).setDuration(duration).start();
        out.animate().translationY(-shift).alpha(0f).setDuration(duration)
                .withEndAction(() -> out.setTranslationY(0f)).start();
    }

    /**
     * Tanggal Hijriah hari ini, atau null kalau jadwal solat lokasi ini belum ada atau harinya di luar
     * tabel kalender (lihat HijriCalendar).
     *
     * Hari Hijriah mulai saat maghrib, jadi lewat maghrib hari Masehi ini sudah milik tanggal Hijriah
     * besok: malam pertama Ramadan tampil sebagai 1 Ramadan sejak maghrib sore sebelumnya.
     */
    static String hijriToday(Context context) {
        long maghrib = PrayerTimes.maghribToday(context);
        if (maghrib <= 0) return null;
        LocalDate day = LocalDate.now();
        if (System.currentTimeMillis() >= maghrib) day = day.plusDays(1);
        return HijriCalendar.format(day);
    }
}
