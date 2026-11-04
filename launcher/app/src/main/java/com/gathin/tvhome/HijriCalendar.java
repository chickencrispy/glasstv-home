package com.gathin.tvhome;

import java.time.LocalDate;

/**
 * Tanggal Hijriah menurut Kalender Hijriah Global Tunggal (KHGT) Muhammadiyah.
 *
 * Hanya tanggal 1 tiap bulan yang disimpan; tanggal lain dihitung dari jarak hari ke tanggal 1 bulan
 * berjalan. Di luar rentang tabel {@link #format} mengembalikan null, supaya pemanggil kembali
 * menampilkan tanggal Masehi alih-alih menebak.
 *
 * Ini bukan {@code java.time.chrono.HijrahDate}: kalender itu Umm al-Qura, dan untuk November 2026 ia
 * mendahului KHGT satu hari (25 vs 24 Jumadil-Awal 1448).
 *
 * SUMBER TABEL: https://khgt.muhammadiyah.or.id/kalendar-hijriah, diambil 5 November 2026 lewat
 * pemilih tahun di halaman itu (tahun 1447 H sampai 1454 H). Tanggal 1 tiap bulan dibaca dari sel
 * hari pertama setiap bulan; seluruh 95 hari istimewa yang situs itu sebutkan cocok dengan tabel ini,
 * dan selisih antar bulan selalu sama dengan jumlah sel hari yang situs itu isi (29 atau 30),
 * jadi tabelnya utuh tanpa lubang. Rentang: 26 Juni 2025 sampai 31 Maret 2033.
 *
 * Yang perlu diingat kalau tabel ini diganti: KHGT menaruh tanggal Hijriah pada hari Masehi yang
 * siangnya jatuh di tanggal itu, sama seperti penanggalan Indonesia pada umumnya.
 * Lihat docs/specs/tanggal-hijriah.md.
 */
final class HijriCalendar {

    /** Tahun Hijriah pertama di tabel. */
    static final int FIRST_YEAR = 1447;

    /**
     * Ejaan Indonesia, bukan Melayu (Rajab, bukan Rejab), mengikuti ejaan KHGT untuk Syakban, dengan
     * nama gabungan ditulis berhubung: Rabiul-Awal, Rabiul-Akhir, Jumadil-Awal, Jumadil-Akhir.
     */
    static final String[] MONTH_NAMES = {
            "Muharam", "Safar", "Rabiul-Awal", "Rabiul-Akhir", "Jumadil-Awal", "Jumadil-Akhir",
            "Rajab", "Syakban", "Ramadan", "Syawal", "Zulkaidah", "Zulhijah"
    };

    /**
     * Hari epoch ({@link LocalDate#toEpochDay()}) tanggal 1 tiap bulan, berurutan, ditutup satu entri
     * terakhir: tanggal 1 bulan sesudah bulan terakhir, dipakai sebagai batas atas tabel.
     * Panjang tiap bulan 29 atau 30 hari, jadi entri berurutan berjarak 29 atau 30.
     */
    private static final long[] MONTH_STARTS = {
            // 1447 H
            20265L, 20295L, 20324L, 20354L, 20384L, 20413L, 20443L, 20473L, 20502L, 20532L, 20561L, 20591L,
            // 1448 H
            20620L, 20649L, 20679L, 20708L, 20738L, 20767L, 20797L, 20827L, 20857L, 20886L, 20916L, 20945L,
            // 1449 H
            20975L, 21004L, 21033L, 21063L, 21092L, 21122L, 21151L, 21181L, 21211L, 21240L, 21270L, 21300L,
            // 1450 H
            21329L, 21359L, 21388L, 21417L, 21447L, 21476L, 21505L, 21535L, 21565L, 21594L, 21624L, 21654L,
            // 1451 H
            21684L, 21713L, 21743L, 21772L, 21801L, 21831L, 21860L, 21889L, 21919L, 21949L, 21978L, 22008L,
            // 1452 H
            22038L, 22067L, 22097L, 22126L, 22156L, 22185L, 22215L, 22244L, 22274L, 22303L, 22332L, 22362L,
            // 1453 H
            22392L, 22421L, 22451L, 22481L, 22510L, 22540L, 22569L, 22599L, 22628L, 22658L, 22687L, 22717L,
            // 1454 H
            22746L, 22775L, 22805L, 22835L, 22864L, 22894L, 22924L, 22953L, 22983L, 23012L, 23042L, 23071L,
            23101L   // 1 Muharam 1455: batas akhir tabel
    };

    private HijriCalendar() {
    }

    /**
     * "12 Jumadil-Awal 1448 H" untuk satu hari Masehi, atau null kalau harinya di luar tabel.
     *
     * Hari yang dimaksud adalah hari Masehi yang <em>siangnya</em> jatuh pada hari Hijriah itu:
     * memanggil ini dengan 8 Februari 2027 memberi "1 Ramadan", karena malam pertama Ramadan sudah
     * mulai sejak maghrib 7 Februari. Perhitungan pergeseran maghrib ada di pemanggil.
     */
    static String format(LocalDate day) {
        long epochDay = day.toEpochDay();
        int index = monthIndex(epochDay);
        if (index < 0) return null;
        int dayOfMonth = (int) (epochDay - MONTH_STARTS[index]) + 1;
        return dayOfMonth + " " + MONTH_NAMES[index % 12] + " " + (FIRST_YEAR + index / 12) + " H";
    }

    /** Tanggal 1 tiap bulan plus batas akhir; untuk uji. */
    static long[] monthStarts() {
        return MONTH_STARTS.clone();
    }

    /** Indeks terbesar yang {@code MONTH_STARTS[i] <= epochDay < MONTH_STARTS[i + 1]}, atau -1 di luar tabel. */
    private static int monthIndex(long epochDay) {
        int last = MONTH_STARTS.length - 2;
        if (epochDay < MONTH_STARTS[0] || epochDay >= MONTH_STARTS[last + 1]) return -1;
        int lo = 0;
        int hi = last;
        while (lo < hi) {
            int mid = (lo + hi + 1) >>> 1;
            if (MONTH_STARTS[mid] <= epochDay) lo = mid;
            else hi = mid - 1;
        }
        return lo;
    }
}
