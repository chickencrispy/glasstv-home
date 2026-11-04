package com.gathin.tvhome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.time.LocalDate;

/** Tabel tanggal 1 tiap bulan Hijriah (KHGT Muhammadiyah), dan pencarian tanggal dari hari Masehi. */
public class HijriCalendarTest {

    @Test
    public void tableHoldsEightWholeYearsPlusItsEnd() {
        long[] starts = HijriCalendar.monthStarts();
        assertEquals("96 bulan ditambah satu batas akhir", 8 * 12 + 1, starts.length);
        assertEquals(LocalDate.of(2025, 6, 26).toEpochDay(), starts[0]);
        assertEquals(LocalDate.of(2033, 4, 1).toEpochDay(), starts[starts.length - 1]);
        for (int i = 1; i < starts.length; i++) {
            long gap = starts[i] - starts[i - 1];
            assertTrue("bulan ke-" + i + " panjangnya " + gap + " hari", gap == 29 || gap == 30);
        }
    }

    @Test
    public void firstOfEachMonthReadsBackItsOwnMonth() {
        long[] starts = HijriCalendar.monthStarts();
        for (int i = 0; i < starts.length - 1; i++) {
            LocalDate day = LocalDate.ofEpochDay(starts[i]);
            String expected = "1 " + HijriCalendar.MONTH_NAMES[i % 12] + " " + (HijriCalendar.FIRST_YEAR + i / 12) + " H";
            assertEquals(day.toString(), expected, HijriCalendar.format(day));
        }
    }

    /** Tanggal 1 tiap tahun, dari halaman kalender KHGT; yang 1448 juga tertulis di beranda situsnya. */
    @Test
    public void startsOfTheYearsMatchTheSourcePage() {
        assertEquals("1 Muharam 1447 H", HijriCalendar.format(LocalDate.of(2025, 6, 26)));
        assertEquals("1 Muharam 1448 H", HijriCalendar.format(LocalDate.of(2026, 6, 16)));
        assertEquals("1 Muharam 1449 H", HijriCalendar.format(LocalDate.of(2027, 6, 6)));
        assertEquals("1 Muharam 1450 H", HijriCalendar.format(LocalDate.of(2028, 5, 25)));
        assertEquals("1 Muharam 1451 H", HijriCalendar.format(LocalDate.of(2029, 5, 15)));
        assertEquals("1 Muharam 1452 H", HijriCalendar.format(LocalDate.of(2030, 5, 4)));
    }

    @Test
    public void countsTheDayFromTheFirstOfItsMonth() {
        assertEquals("25 Jumadil-Awal 1448 H", HijriCalendar.format(LocalDate.of(2026, 11, 5)));
        assertEquals("30 Rabiul-Akhir 1448 H", HijriCalendar.format(LocalDate.of(2026, 10, 11)));
        assertEquals("1 Jumadil-Akhir 1448 H", HijriCalendar.format(LocalDate.of(2026, 11, 10)));
        assertEquals("1 Ramadan 1448 H", HijriCalendar.format(LocalDate.of(2027, 2, 8)));
        assertEquals("1 Syawal 1448 H", HijriCalendar.format(LocalDate.of(2027, 3, 9)));
        assertEquals("30 Zulhijah 1451 H", HijriCalendar.format(LocalDate.of(2030, 5, 3)));
        assertEquals("30 Zulhijah 1454 H", HijriCalendar.format(LocalDate.of(2033, 3, 31)));
    }

    @Test
    public void outsideTheTableThereIsNoDate() {
        assertNull(HijriCalendar.format(LocalDate.of(2025, 6, 25)));
        assertNull(HijriCalendar.format(LocalDate.of(2033, 4, 1)));
        assertNull(HijriCalendar.format(LocalDate.of(1999, 1, 1)));
    }
}
