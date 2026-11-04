# Tanggal Hijriah bergantian, spec

Baris tanggal di layar utama menampilkan tanggal Masehi. Spec ini menambah tanggal Hijriah yang
tampil bergantian dengan tanggal Masehi, dengan animasi pada tiap pergantian. Kedua screensaver
ikut bergantian dengan cara yang sama: layar hitam yang muncul sendiri setelah 10 menit TV
menganggur (overlay di `MainActivity`, inilah yang dipakai sehari-hari) dan `ClockDream` untuk TV
yang memakai screensaver sistem. Keduanya memakai layout dan `DateAlternator` yang sama, jadi
tidak mungkin menampilkan tanggal Hijriah yang berbeda.

Tujuannya: penghuni rumah yang memakai penanggalan Hijriah tidak perlu membuka aplikasi lain
untuk tahu tanggalnya, sementara tanggal Masehi tetap jadi acuan utama.

## Perilaku

| | |
|---|---|
| Yang bergantian | Hanya baris tanggal: `@id/date`/`@id/dateHijri` di Home, `@id/nightstandDate`/`@id/nightstandDateHijri` di screensaver. Nama hari (`@id/ampm`) tidak berubah |
| Masehi | 10 detik |
| Hijriah | 5 detik |
| Animasi | Slide naik: teks lama naik sambil memudar, teks baru masuk dari bawah, ±300 ms |
| Lebar | Dikunci ke ukuran teks terpanjang dari kedua tanggal, supaya blok tidak bergoyang saat berganti |

Contoh isi baris Home: `5 November 2026` ⇄ `25 Jumadil-Awal 1448 H`. Di screensaver barisnya
memuat nama hari: `Kamis, 5 November 2026` ⇄ `25 Jumadil-Awal 1448 H` (tanggal Hijriah tidak
memakai nama hari, karena harinya mulai saat maghrib).

## Kalender

Sumbernya **Kalender Hijriah Global Tunggal (KHGT) Muhammadiyah**,
<https://khgt.muhammadiyah.or.id/kalendar-hijriah>.

Bukan `java.time.chrono.HijrahDate` bawaan Android: kalender itu Umm al-Qura, kalender sipil Arab
Saudi, dan pada 13 dari 96 tanggal 1 bulan dalam rentang tabel di bawah ia berbeda satu hari dari
KHGT. Yang paling dekat: 10 November 2026 menurut KHGT sudah 1 Jumadil-Akhir 1448 H, sedangkan
Umm al-Qura masih 30 Jumadil-Awal 1448 H.

Tanggal 1 tiap bulan dibundel di aplikasi sebagai tabel, bukan dihitung dan bukan diunduh:
launcher ini tidak memakai jaringan selain gambar places, dan hasil hisab sendiri belum tentu sama
dengan kalender yang diterbitkan Muhammadiyah.

Di luar rentang tabel, baris tanggal menampilkan Masehi saja. Launcher tidak menebak.

Nama bulan memakai ejaan Indonesia, dengan nama gabungan ditulis berhubung: Muharam, Safar,
Rabiul-Awal, Rabiul-Akhir, Jumadil-Awal, Jumadil-Akhir, Rajab, Syakban, Ramadan, Syawal,
Zulkaidah, Zulhijah. Diakhiri `H`.

## Batas ganti hari

Hari Hijriah mulai saat maghrib, jadi **setelah maghrib tanggal langsung maju satu hari**, bukan
tetap sampai tengah malam. KHGT sendiri menaruh tanggal Hijriah pada hari Masehi yang siangnya
jatuh di tanggal itu, jadi aturan ini yang membuat layar mengikuti hari Hijriah, bukan tanggal
kotak kalender.

| Waktu | Yang tampil |
|---|---|
| 7 Feb 2027, siang | 30 Syakban 1448 H |
| 7 Feb 2027, lewat maghrib | 1 Ramadan 1448 H |
| 8 Feb 2027 (kalender KHGT: 1 Ramadan), siang | 1 Ramadan 1448 H |
| 8 Feb 2027, lewat maghrib | 2 Ramadan 1448 H |

Jam maghrib diambil dari takwim lokasi/zona pengguna yang sudah ada
(`PrayerTimes.maghribToday()`). Kalau jam takwim belum tersedia, baris tanggal tetap menampilkan
Masehi — launcher tidak memakai hitungan cadangan untuk batas hari.

## Pengaturan

| Kunci `config.json` | Default | Arti |
|---|---|---|
| `hijri_date` | `true` | `false`: baris tanggal di Home dan di screensaver kembali seperti semula, Masehi saja |

`reduced_motion: true` mematikan slide: pergantian jadi fade singkat. Screensaver membaca
`config.xml` dan `config.json` yang sama, jadi kedua layar memakai setelan yang sama.

## Batasan yang harus tetap dipenuhi

- Tidak ada service background baru. Timer pergantian menempel di `Handler` yang sudah dipakai
  (`minuteTick` di Home, `tick` di screensaver), berhenti saat Home atau screensaver tidak di
  depan, lanjut lagi di `onResume`.
- APK tetap di bawah 800 KB.
- Tidak ada permintaan jaringan baru.
- `launcher/app/build.gradle` dan jumlah string tidak berubah kecuali yang disebut di atas.

## Berkas

| Berkas | Perubahan |
|---|---|
| `HijriCalendar.java` (baru) | Tabel KHGT, pencarian tanggal Hijriah, nama bulan Indonesia |
| `DateAlternator.java` (baru) | Pertukaran 10/5 detik dan animasi gesernya, dipakai Home dan screensaver; juga `hijriToday()` (maghrib + tabel) |
| `activity_main.xml`, `view_nightstand.xml` | Dua teks bertumpuk untuk baris tanggal, lebar terkunci |
| `MainActivity.java` | `updateDateAndPeriod()` memperbarui tanggal Hijriah tiap menit, dan `updateNightstandScreensaver()` untuk overlay screensaver 10 menit; pertukaran lewat `DateAlternator` |
| `ClockDream.java` | Baris tanggal screensaver sistem ikut bergantian, dengan setelan yang sama |
| `Config.java`, `res/values/config.xml` | kunci `hijri_date` |
| `themes/README.md` | baris untuk `hijri_date` |
| `HijriCalendarTest.java` | uji tabel: tanggal 1 tiap bulan berurutan, 12 entri per tahun, pencarian tanggal |

## Isi tabel dan statusnya

`HijriCalendar.MONTH_STARTS` memuat 96 tanggal 1 bulan, 1447 H (26 Juni 2025) sampai 1454 H,
ditutup batas 1 Muharam 1455 H (1 April 2033). Tiap bulan 29 atau 30 hari. Ini seluruh rentang
yang situs KHGT sediakan lewat pemilih tahunnya (1447–1454 H), diambil 5 November 2026.

**Status: terverifikasi terhadap situsnya, bukan terhadap buku cetak.** Cara mengambil dan
memeriksanya:

1. Tiap bulan di situs dirender sebagai kotak hari; sel hari pertama memberi tanggal Masehi
   tanggal 1 bulan Hijriah itu, dan kotak kosong di awal grid memberi hari pertama pekan.
2. Rantai bulannya utuh: selisih tanggal 1 bulan ke bulan selalu sama dengan jumlah sel hari yang
   situs isi (29 atau 30), dari 1447 sampai batas akhir 1455 H.
3. Seluruh 95 hari istimewa yang situs itu sebutkan ("9 Muharam 1448 H / 24 Jun 2026",
   "1 Muharam 1448 H / 16 Jun 2026", dan seterusnya) cocok dengan tabel ini, tanpa selisih.
4. Beranda situsnya, 5 November 2026, menulis "Kamis, 25 Jumadil-Awal 1448 H / 05 November 2026" —
   sama dengan yang dihasilkan tabel ini.

Yang belum: mencocokkan dengan buku/takwim KHGT cetak terbitan Majelis Tarjih, dan mengulang
pengambilan kalau Muhammadiyah merevisi kalender jauh hari. Kalau tabelnya diganti, cukup ganti
`MONTH_STARTS` dan `FIRST_YEAR`, lalu jalankan `HijriCalendarTest`.