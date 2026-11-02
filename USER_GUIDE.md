# Panduan Pengguna Glass TV Home (Ramah Pemula) 📺✨

Selamat datang di **Glass TV Home**! Panduan ini disusun khusus untuk pengguna sehari-hari dan pemula agar Anda dapat dengan mudah memasang, melakukan debloat pada TV untuk performa yang sangat cepat tanpa iklan, menavigasi dengan lancar menggunakan remote TV, dan menikmati semua fitur ambient pintar tanpa repot.

---

## 🌟 1. Apa itu Glass TV Home?

Glass TV Home adalah launcher beranda modern dan ringan sebagai pengganti bawaan untuk Android TV dan Google TV (kompatibel dengan Xiaomi Mi Box S, Mi TV Stick, Sony Bravia, TCL, Sharp, Philips, Mecool, Chromecast with Google TV, dan lainnya):

* 🚀 **Tanpa Iklan & Sangat Ringan**: Hanya sekitar 570 KB. Menghemat lebih dari 400 MB RAM dibandingkan launcher bawaan pabrikan yang lambat.
* 🪟 **Estetika Kaca Buram (Gaya tvOS)**: Antarmuka modern terinspirasi Apple TV dengan animasi fokus yang halus dan dock kaca mengambang.
* ⛅ **Dashboard Cuaca & Kualitas Udara (API / AQI)**: Suhu ambient langsung, "terasa seperti", kelembapan, indeks UV, kecepatan angin, matahari terbit/terbenam, prakiraan 12 jam, prakiraan 7 hari, dan pembacaan Indeks Kualitas Udara resmi (APIMS).
* 🌙 **Screensaver StandBy & Mati Otomatis 2 Jam**: Masuk ke screensaver ambient hitam bergaya meja samping tempat tidur setelah 10 menit tidak ada aktivitas (aman dari burn-in untuk OLED/LED), dan secara otomatis mematikan layar TV setelah 2 jam standby tidur untuk menghemat listrik.

---

## 📦 Unduhan APK Langsung

Bagi pengguna yang ingin mengunduh dan memasang APK yang sudah dikompilasi secara langsung:
* 📥 **APK Rilis Resmi**: [**glass-tv-home.apk**](https://github.com/chickencrispy/glasstv-home/raw/main/release/glass-tv-home.apk)
* 📁 **Lokasi Repositori**: [`release/glass-tv-home.apk`](release/glass-tv-home.apk)
* ⚡ **Versi**: **v3.3.1-id** (Edisi Indonesia: 🍏 Dashboard Bento Grid bergaya Apple Weather, 🌧️ latar belakang cuaca yang dipercepat perangkat keras, 📡 peta radar Doppler, 🧭 kompas angin, 🌙 fase bulan 3D, 🎮 profil suara konsol: PS5, Nintendo Switch, Xbox & Senyap, 🔄 pembaruan otomatis OTA dalam aplikasi).

> [!NOTE]
> ### 🇲🇾 Pengguna Unifi TV Box (TM Unifi Plus Box): Kompatibilitas & Keamanan Garansi
> * **Bisakah saya menggunakan Glass TV Home di Unifi TV Box saya? YA, 100%!** Unifi Plus Box (Skyworth HP40A3, ZTE B866V2K, dan box Android TV TM lainnya) menjalankan Android TV bersertifikat. Aplikasi **unifi TV** resmi (untuk saluran langsung dan langganan TV Anda) tetap berfungsi penuh dan muncul tepat di dock aplikasi Anda.
> * **Apakah ini akan membatalkan garansi TM saya? TIDAK, SAMA SEKALI TIDAK!** Glass TV Home dipasang sebagai APK ruang pengguna standar. Aplikasi ini **TIDAK memerlukan root**, **TIDAK membuka bootloader**, dan **TIDAK mengubah firmware sistem**. Aplikasi ini dapat di-uninstall kapan saja melalui Pengaturan TV standar atau dihapus dengan Factory Reset sederhana, mengembalikan box ke kondisi 100% bawaan TM.

---

## 🤖 2. Cara Termudah: Biarkan AI Mengerjakan Semuanya Secara Otomatis! (Prompt 1 Baris)

Anda **tidak perlu keterampilan coding** atau pengetahuan terminal teknis. Setelah mengaktifkan mode pengembang di TV, cukup tempel **satu prompt** ini ke asisten coding AI mana pun (seperti Google Antigravity [Gratis] atau Claude Code):

```text
install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
```

*(Alias bahasa Inggris: `install this on my home tv: https://github.com/chickencrispy/glasstv-home`)*

AI akan menangani **SEMUANYA** secara otomatis dari awal hingga akhir!

### 🔄 Memperbarui Instalasi yang Ada (Patching AI 1 Baris)
Sudah menjalankan Glass TV Home dan ingin memperbarui ke patch atau versi terbaru? Cukup kirim satu baris ini ke asisten AI Anda:
```text
update patch latest glass tv dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
```
*(Bahasa Inggris: `update glass tv to the latest patch on my tv: https://github.com/chickencrispy/glasstv-home`)*

AI terhubung melalui ADB Wi-Fi, memperbarui APK dalam 5 detik (`adb install -r`), dan menjalankan ulang layar beranda. **Aplikasi dock, wallpaper, dan pengaturan suara yang telah Anda sesuaikan tetap 100% terjaga!**

### 💡 Asisten AI yang Didukung:
1. **Google Antigravity (Gratis / Tanpa Penyiapan)** ⭐ *Sangat Direkomendasikan*:
   - Agen coding AI resmi dari Google DeepMind dengan kemampuan shell terminal penuh, alat otomasi, dan eksekusi skrip secara gratis.
2. **Claude Code (Langganan Berbayar Anthropic)**:
   - Agen coding CLI yang canggih dari Anthropic yang berjalan di dalam terminal Anda.
3. **Cursor / Windsurf IDE (Freemium / Berbayar)**:
   - Editor kode AI dengan shell terminal terintegrasi.

---

### 📋 Langkah 1: Aktifkan Mode Pengembang & Debugging di TV Anda
Sebelum AI dapat menjangkau TV Anda melalui Wi-Fi, lakukan dua tindakan sederhana dengan remote ini:
1. Pada remote TV Anda, buka **Pengaturan ⚙️**.
2. Pilih **Preferensi Perangkat** (atau **Sistem** pada Google TV yang lebih baru).
3. Pilih **Tentang** $\rightarrow$ Gulir ke bawah ke **Build** (atau *Android TV OS Build*).
4. **Tekan tombol OK pada remote Anda 7 kali dengan cepat** hingga muncul notifikasi toast: *"Anda sekarang menjadi pengembang!"*.
5. Tekan tombol *Kembali* satu kali. Buka menu baru: **Opsi Pengembang**.
6. Temukan dan **AKTIFKAN**:
   - ✅ **Debugging USB** (Aktifkan).
   - ✅ **Debugging nirkabel / Debugging jaringan** (Aktifkan).
7. Pastikan komputer/ponsel Anda dan TV terhubung ke **jaringan Wi-Fi yang sama**.

---

### 📋 Langkah 2: Tempel Prompt 1 Baris Ini ke Asisten AI Anda
Buka **Google Antigravity**, **Claude Code**, atau **Cursor** di laptop/komputer Anda. Salin dan tempel satu prompt ini:

```text
install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
```

> **Apa yang akan dilakukan AI secara otomatis setelah Anda mengirim prompt ini:**
> 1. AI akan meminta alamat IP TV Anda (atau memindai subnet Wi-Fi rumah Anda untuk menemukan TV secara otomatis).
> 2. AI akan meminta lokasi Anda di Malaysia (misalnya Putrajaya, Cheras, Shah Alam, Melaka, JB, Penang, Kuantan, dll.) untuk mengkalibrasi stasiun kabut asap APIMS.
> 3. Terhubung ke TV Anda melalui Wi-Fi via ADB (`adb connect <ip>:5555`).
> 4. Membuat cadangan pengaman konfigurasi paket pabrik TV Anda.
> 5. Menghapus bloatware pabrikan yang lambat, menekan iklan promosi, dan mengaktifkan AdGuard Private DNS untuk memblokir analitik latar belakang.
> 6. Memasang APK Glass TV Home yang sangat ringan (di bawah 600 KB) sebagai launcher beranda utama Anda.
> 7. Mengambil tangkapan layar langsung dari TV Anda untuk memastikan semuanya berjalan lancar!

*(Opsional: Jika Anda ingin memberikan semua info sekaligus dalam satu pesan tanpa bolak-balik:)*
```text
install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
TV Model: [e.g. Xiaomi Mi Box S]
TV IP: [e.g. 192.168.68.102]
My Location: [e.g. Putrajaya / Cheras / Shah Alam / Johor Bahru / Penang]
```

> [!IMPORTANT]
> **🚨 Instruksi Wajib untuk Asisten AI (Aturan Asisten AI)**:
> Ketika pengguna memberikan prompt `install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home`, AI **WAJIB** bertanya kepada pengguna:
> 1. *"Berapa alamat IP TV Anda di Pengaturan -> Jaringan & Internet (atau sesuai merek TV Anda)?"* (jika tidak ditemukan otomatis)
> 2. *"Di mana lokasi Anda di Malaysia (misalnya Putrajaya, Cheras, Shah Alam, Melaka, Johor Bahru, Penang, Kuantan, Kuching, Kota Kinabalu, dll.) agar pembacaan kabut asap / IPU resmi Jabatan Alam Sekitar (DOE / APIMS) dapat dikonfigurasi secara otomatis?"*
> 
> AI dapat langsung memasang APK yang sudah dibangun dari `release/glass-tv-home.apk` atau membangunnya, menjalankan debloat, dan mengatur lokasi pengguna melalui:
> ```bash
> adb shell am broadcast -a com.gathin.tvhome.SET_LOCATION -p com.gathin.tvhome --es query "<USER_LOCATION>"
> ```
> *(Jika pengguna tidak menentukan lokasi, preset bawaan adalah **Putrajaya** / Stasiun CA17W).*

---

### 📋 Langkah 3: Klik "Izinkan" di Layar TV Anda
Saat AI memulai koneksi, prompt otorisasi akan muncul di layar TV Anda:
* *"Izinkan debugging USB dari komputer ini?"*
* Centang kotaknya: ☑ **"Selalu izinkan dari komputer ini"**
* Pilih **OK / Izinkan**.

AI akan menangani sisanya: membersihkan bloatware yang lambat, mengaktifkan pemblokir iklan, memasang Glass TV Home, dan menunjukkan tangkapan layar TV Anda yang bersih dan responsif!

---

### ↩️ Cara Membatalkan / Memulihkan Launcher Asli
Semua modifikasi yang dilakukan AI **100% aman dan dapat dikembalikan**:
* AI menonaktifkan aplikasi alih-alih menghapus paket sistem secara permanen.
* Skrip pemulihan otomatis (`restore.sh` / `restore.ps1`) disimpan.
* Jika Anda ingin kembali ke launcher asli pabrikan, cukup beri prompt kepada AI:
  > *"Tolong kembalikan TV saya ke launcher dan pengaturan pabrik aslinya."*

---

## 🎮 3. Navigasi Remote Control (D-Pad)

Glass TV Home dirancang untuk remote TV standar:

| Tombol Remote | Aksi / Fungsi |
| :--- | :--- |
| **Tombol HOME (Tekan Sekali)** | Kembali ke dock beranda dari aplikasi pihak ketiga mana pun. Jika sudah di beranda, aktifkan screensaver ambient. |
| **TAHAN HOME (Tekan Lama)** | Membuka popup Pengaturan Cepat mengambang secara global dari dalam aplikasi mana pun (Netflix, YouTube, dll.). |
| **Tombol UP (▲)** | Menavigasi ke rak atas untuk menyorot **Pil Cuaca & AQI**, Input, Wi-Fi, atau **Tombol Pengaturan (⚙️)**. |
| **Tombol OK / Tengah** | • Tekan pada aplikasi untuk membukanya.<br>• Tekan pada pil cuaca untuk membuka **Dashboard Cuaca & Kualitas Udara Layar Penuh**.<br>• Tekan **Pengaturan (⚙️)** untuk membuka **Menu Dropdown Kaca Buram** (Wallpaper, Lokasi Kabut Asap, Pengaturan Android).<br>• **Tekan Lama** pada aplikasi untuk memilihnya dan mengatur ulang/menyematkannya ke dock. |
| **Tombol DOWN (▼)** | Mengembalikan fokus ke bawah ke dock aplikasi utama. |
| **KIRI / KANAN (◀ / ▶)** | Berpindah dengan mulus antar aplikasi dock atau pil atas dengan transisi cahaya kaca yang halus. |
| **Tombol BACK (↩)** | Menutup Dashboard Cuaca, Dialog Pengaturan, atau screensaver dan kembali ke dock beranda. |

---

## 📱 4. Penjelasan Fitur Utama

### A. Pusat Cuaca & Kualitas Udara (AQI)
* Terletak di bawah jam digital (misalnya, `☁ 27° Overcast  •  API 106 Unhealthy`).
* **Tekan UP (▲) lalu tekan OK** untuk membuka antarmuka cuaca Leanback layar penuh:
  1. **Kondisi Saat Ini**: Suhu, "Terasa Seperti", kelembapan, kecepatan angin, indeks UV, waktu matahari terbit dan terbenam.
  2. **Indeks Kualitas Udara Resmi (JAS Malaysia APIMS / IPU)**: Data ground real-time langsung dari portal APIMS Department of Environment (JAS) dengan warna skala resmi Malaysia (Baik: Hijau, Sedang: Biru Langit, Tidak Sehat: Oranye, Sangat Tidak Sehat: Merah, Berbahaya: Marun) dan panduan kesehatan resmi.
  3. **Prakiraan Per Jam 12 Jam**: Kartu prakiraan per jam dengan persentase peluang hujan.
  4. **Prakiraan 7 Hari**: Prakiraan cuaca harian sepanjang minggu mendatang.

📍 **Pengaturan Lokasi Manual Langsung via Remote TV (Untuk Instalasi APK Mandiri):**
Pengguna yang memasang APK secara manual atau pindah lokasi **TIDAK MEMERLUKAN KOMPUTER ATAU AI** untuk mengonfigurasi stasiunnya:
1. Di layar beranda, tekan **UP (▲)** dan tekan **OK** pada pil cuaca untuk membuka Dashboard Cuaca.
2. Tekan **UP (▲)** lagi untuk menyorot tombol **📍 Tukar Stesen / Change** di samping nama lokasi.
3. Tekan **OK**. Modal dialog akan muncul dengan 30+ stasiun DOE Malaysia resmi di seluruh negara bagian:
   - *Putrajaya, Cheras (KL), Batu Muda (KL), Petaling Jaya, Shah Alam, Klang, Banting, Bandaraya Melaka, Alor Gajah, Bukit Rambai, Seremban, Nilai, Port Dickson, Larkin (JB), Pasir Gudang, Batu Pahat, Kluang, Tangkak, Seberang Jaya (Penang), Minden (USM), Balik Pulau, Ipoh, Taiping, Seri Manjung, Alor Setar, Sungai Petani, Langkawi, Kangar, Kuantan, Temerloh, Kuala Terengganu, Kemaman, Kota Bharu, Kota Kinabalu, Sandakan, Tawau, Kuching, Miri, Bintulu, Sibu, Labuan*.
4. Gulir dengan tombol **UP / DOWN** pada remote untuk memilih stasiun Anda dan tekan **OK**.
5. Pembacaan IPU JAS resmi langsung menyesuaikan dengan kota pilihan Anda!

### B. Wallpaper 4K Dinamis & Pengalih Kategori
* **Tema Terkurasi**: Ganti latar belakang beranda Anda di antara kategori 4K terkurasi langsung dari remote:
  - 🌿 **Natural (Bawaan / Bing Places)**: Landmark dunia, gunung, danau, dan lanskap alam dengan cerita lokasi informatif yang diperbarui setiap minggu.
  - 🇲🇾 **Malaysia (Terkurasi 14 Negara Bagian & Wilayah Federal)**: Pilih wallpaper khusus untuk salah satu dari 14 negara bagian Malaysia atau putar semua negara bagian sekaligus melalui sub-dialog khusus:
    - 📍 **Johor**: Pulau Rawa Mersing & Garis Pantai Tropis.
    - 📍 **Kedah**: Jembatan Langit Langkawi Gunung Mat Cincang & Laut Zamrud.
    - 📍 **Kelantan**: Pantai Cahaya Bulan & Hutan Pinus Pantai Senok Kota Bharu.
    - 📍 **Melaka**: Kota Bersejarah UNESCO & Promenade Malam Sungai Melaka.
    - 📍 **Negeri Sembilan**: Pantai Cahaya Negeri & Teluk Kemang Port Dickson.
    - 📍 **Pahang**: Perkebunan Teh Bergelombang Cameron Highlands & Lembah Teh Bharat.
    - 📍 **Perak**: Kota Warisan Kolonial Ipoh, Kellie's Castle & Gua Batu Kapur Kek Lok Tong.
    - 📍 **Perlis**: Gua Batu Kapur Gua Kelam & Kaki Bukit Pegunungan Nakawan.
    - 📍 **Pulau Pinang**: Jalan Warisan UNESCO Georgetown & Kuil Kek Lok Si.
    - 📍 **Sabah**: Puncak Gunung Kinabalu & Puncak Selatan Kundasang.
    - 📍 **Sarawak**: Puncak Mulu UNESCO, Tepi Air Kuching & Garis Pantai Miri.
    - 📍 **Selangor**: Batu Caves Sri Subramaniar & Masjid Biru Shah Alam.
    - 📍 **Terengganu**: Surga Laut Pulau Perhentian & Masjid Kristal Kuala Terengganu.
    - 📍 **Wilayah Persekutuan**: Menara Kembar Petronas KLCC & Pemandangan Malam Kota Kuala Lumpur.
  - 🏙️ **Skyscraper**: Arsitektur modern dan cakrawala terkenal (Manhattan NYC, Chicago River, Dubai Marina, Hong Kong Cityscape).
  - 🌌 **Deep Space**: Nebula berwarna-warni, hamparan bintang, dan pemandangan kosmik.
  - 🌊 **Ocean & Coastal**: Cakrawala pesisir yang murni, ombak, dan laut biru.
  - 🔲 **Minimalist Dark**: Estetika bertekstur dalam yang elegan, hemat daya, dan aman untuk OLED.
* **📂 Sub-Dropdown Pemilih Negara Bagian (UI TV Bersih)**:
  - Klik **`🇲🇾 Malaysia (Pilih Negeri ▶)`** pada menu wallpaper untuk membuka dialog leanback yang mencantumkan semua 14 negara bagian Malaysia.
  - Pilihan aktif Anda ditampilkan tepat di menu utama (misalnya `🇲🇾 Malaysia: Johor (Tukar Negeri ▶)`).
  - Rak TV secara otomatis menyorot lokasi, nama negara bagian, dan cerita historis setiap landmark Malaysia!
* **📲 Unggah Wallpaper via Ponsel (Kode QR)**:
  - Pilih **`📲 Upload Wallpaper via Phone (QR Code)`** dari opsi wallpaper untuk menampilkan kode QR yang bersih dan tautan web lokal.
  - Pindai dengan ponsel apa pun di jaringan Wi-Fi yang sama untuk mengunggah foto keluarga kustom, foto liburan, atau karya seni langsung ke TV Anda tanpa akun cloud atau aplikasi pihak ketiga.
* **📱 Pengelola Foto di TV & Kunci Wallpaper Tunggal**:
  - Setelah mengunggah foto, kelola dengan mudah langsung dari browser ponsel Anda atau melalui menu D-pad TV: **`🗑️ Urus Gambar Dimuat Naik (Pilih / Padam)`**.
  - **Kunci Wallpaper Tunggal**: Pilih **`📌 Set as Single Wallpaper`** untuk mengunci satu foto secara permanen sebagai latar belakang tanpa rotasi.
  - **Putar Semua Foto**: Pilih **`🔄 Rotate All Uploaded Photos`** untuk bergilir melalui semua foto kustom dalam mode slideshow.
  - **Hapus Tertentu / Hapus Semua**: Hapus foto yang diunggah satu per satu atau hapus semua gambar kustom dengan satu klik.
* **🖼️ Tampilan Bersih untuk Wallpaper Kustom**:
  - Saat wallpaper kustom pribadi aktif, rak teks / deskripsi di sisi kiri sepenuhnya disembunyikan (`View.GONE`) sehingga foto pribadi Anda ditampilkan hingga tepi layar tanpa tertutup keterangan lokasi.
* **Perpindahan Instan**: Pilih **🔀 Next Wallpaper** kapan saja untuk langsung beralih ke foto berikutnya dalam kategori aktif Anda.

### C. Mode Ambient StandBy & Hemat Daya Pintar
* **Menganggur 10 Menit**: Masuk ke screensaver StandBy hitam yang aman untuk OLED, menampilkan angka besar 110sp, tanggal Hijriah Islam, dan waktu salat berikutnya.
* **2 Jam Kemudian**: Jika dibiarkan tanpa sentuhan, layar TV masuk ke standby tidur (`GLOBAL_ACTION_LOCK_SCREEN`) untuk menghemat daya.
* **Bangunkan**: Tekan tombol apa pun pada remote TV untuk membangunkan TV segera.

### D. Menu Dropdown Pengaturan Kaca Buram ⚙️
Mengklik **Pengaturan (ikon roda gigi ⚙️)** di bilah atas akan menampilkan menu kaca mengambang bergaya Apple tvOS:
* 🖼️ **Wallpaper**: Membuka dialog pemilih kategori (*Natural, Skyscraper, Deep Space, Ocean, Minimalist Dark, Next Wallpaper*). Preferensi tetap tersimpan setelah reboot.
* 📍 **Lokasi API Kabut Asap**: Membuka pemilih untuk 30+ stasiun pemantauan kualitas udara resmi DOE Malaysia (JAS APIMS).
* 🔊 **Efek Suara**: Menampilkan tema audio saat ini (misalnya `Sound: PlayStation 5`) dan membuka pemilih profil suara konsol dengan pratinjau langsung.
* 🔄 **Periksa Pembaruan**: Memeriksa GitHub secara langsung untuk rilis atau patch yang lebih baru tanpa memerlukan PC.
* ⚙️ **Semua Pengaturan**: Membuka preferensi sistem Android TV standar.
*(Pintasan: Menekan lama tombol Pengaturan pada remote Anda langsung membuka pengaturan Android standar).*

### E. Efek Suara Konsol & Mode Senyap (PS5, Switch, Xbox) 🎮🔊
Sesuaikan audio navigasi TV Anda dengan profil konsol game ikonik atau senyapkan semua suara sepenuhnya:
* **PlayStation 5 (Bawaan)**: Suara whoosh akustik definisi tinggi yang ramping dan klik yang taktil.
* **Nintendo Switch**: Klik taktil mekanis yang tajam dan bunyi konfirmasi yang ceria.
* **Xbox Series X/S**: Pulsa navigasi mid-bass hangat yang resonan dan lonceng naik dua nada.
* **Senyap / Mati**: Sunyi total untuk sesi film malam yang sangat tenang.
* **Audisi Audio Instan**: Mengetuk tema apa pun di dalam dialog langsung memutar bunyi khasnya.
* **Preferensi Persisten**: Pengaturan tetap tersimpan setelah TV restart melalui SharedPreferences.

### F. Pembaruan OTA di TV & Pemeriksa Patch Otomatis 🔄🚀
Glass TV Home memiliki sistem pembaruan OTA bawaan sehingga Anda dapat memperbarui langsung di TV tanpa komputer:
* **Pemeriksaan Manual**: Buka **Pengaturan (⚙️) $\rightarrow$ Periksa Pembaruan**. Aplikasi menghubungi manifes rilis GitHub dan memberi tahu Anda jika ada versi baru.
* **Pemeriksaan Senyap Saat Mulai**: Launcher diam-diam memeriksa versi baru di latar belakang saat dinyalakan. Jika patch baru ditemukan, dialog changelog akan ditampilkan.
* **Unduh & Pasang 1 Klik**: Mengklik **Unduh & Pasang** mengunduh file pembaruan secara lokal dan membuka pemasang paket native Android TV.
* **Data Tetap Terjaga**: Memperbarui akan mempertahankan semua aplikasi, tata letak dock, dan konfigurasi kustom.

---

## 📲 5. Penyiapan Ponsel (Tanpa PC / Laptop)

Anda **tidak** memerlukan komputer untuk memasang Glass TV Home.

### Metode 1: Ponsel Android via 'Bugjaeger' (Pasang APK + Debloat Langsung)
Aplikasi **Bugjaeger Mobile ADB** (gratis di Google Play Store) memungkinkan ponsel Anda berperan sebagai pengontrol ADB melalui Wi-Fi:

1. Hubungkan ponsel dan TV ke **Wi-Fi yang sama**.
2. Buka **Bugjaeger**, ketuk ikon **Connect 🔌**, masukkan IP TV Anda (misalnya `192.168.68.102:5555`), dan ketuk **Connect**.
3. Terima prompt *"Izinkan debugging USB"* di layar TV Anda.
4. Buka tab **Packages 📦** di Bugjaeger, ketuk **+**, dan pilih `app-release.apk` yang sudah diunduh untuk memasangnya langsung.
5. Di tab **Commands ⚡**, jalankan perintah ini untuk memblokir iklan dan debloat:
   ```bash
   settings put global private_dns_specifier dns.adguard-dns.com
   settings put global private_dns_mode hostname
   pm disable-user --user 0 com.mitv.tvhome
   pm disable-user --user 0 com.xiaomi.mitv.advertise
   ```

---

### Metode 2: Pengguna iPhone (iOS): Bisakah Menggunakan iPhone?
**Ya! Anda dapat dengan mudah memasang APK dan memblokir iklan di TV menggunakan iPhone.**

Karena kebijakan keamanan sandbox Apple App Store, Apple tidak mengizinkan alat ADB tingkat rendah langsung seperti Bugjaeger di iOS. Namun, pengguna iPhone dapat mencapai penyiapan yang bersih dengan cepat:

#### 1. Transfer & Pasang APK menggunakan 'LocalSend':
* Unduh aplikasi **LocalSend** gratis di iPhone Anda (App Store) dan di Android TV Anda (Play Store).
* Unduh `app-release.apk` di Safari iPhone Anda dari repositori GitHub ini.
* Buka LocalSend di kedua perangkat (pada Wi-Fi yang sama) dan kirim APK ke TV Anda dalam 1 ketukan.
* Di TV Anda, klik file APK yang diterima dan pilih **Pasang**.

#### 2. Blokir Iklan & Telemetri Langsung dari Remote TV (Tanpa ADB):
1. Di TV Anda, buka **Pengaturan ⚙️** $\rightarrow$ **Jaringan & Internet**.
2. Pilih **DNS Privat** (ubah dari *Otomatis* ke *Nama host penyedia DNS privat*).
3. Ketik:
   ```text
   dns.adguard-dns.com
   ```
4. Klik **Simpan**. Semua iklan banner seluruh sistem, analitik pabrikan, dan domain pelacakan akan langsung diblokir!

#### 3. Catatan Debloat Mendalam untuk Pengguna iPhone:
Untuk menonaktifkan paket sistem pabrikan yang mendalam (misalnya Xiaomi PatchWall), disarankan menjalankan perintah ADB sekali dari komputer, atau meminjam ponsel Android selama 2 menit. Setelah dinonaktifkan, bloatware akan tetap hilang secara permanen.

---

## 🛠️ 6. Alternatif: Instalasi Manual via Flash Drive USB

Jika Anda lebih suka menggunakan flash drive USB fisik:
1. Unduh [**glass-tv-home.apk**](https://github.com/chickencrispy/glasstv-home/raw/main/release/glass-tv-home.apk) (juga ada di [`release/glass-tv-home.apk`](release/glass-tv-home.apk)) ke flash drive USB Anda.
2. Colokkan flash drive USB ke Android TV atau Xiaomi Mi Box Anda.
3. Buka aplikasi file explorer apa pun di TV (misalnya, *File Commander* atau *FX File Explorer* dari Google Play Store).
4. Klik `app-release.apk` dan pilih **Pasang** (izinkan *"Pasang aplikasi tidak dikenal"* jika diminta).
5. Tekan tombol **Home** pada remote Anda dan pilih **Glass TV Home** sebagai *Selalu*.

---

## ❓ 7. Pertanyaan yang Sering Diajukan (FAQ)

### Q1: Bisakah saya menjalankan Claude Code di ponsel (iOS / Android), dan apakah sandbox iOS memblokir akses jaringan lokal?
* **Persyaratan CLI / Node.js:** Claude Code (`@anthropic-ai/claude-code`) adalah alat terminal interaktif yang memerlukan lingkungan shell **Node.js 18+** lengkap. Kebijakan sandbox iOS secara ketat melarang menjalankan mesin Node.js dan eksekusi shell lokal arbitrer langsung di iPhone.
* **Hambatan Cloud vs Jaringan Lokal:** Claude Code atau AI berbasis cloud mana pun (misalnya Claude web atau ChatGPT web) berjalan di server cloud jarak jauh. Server cloud **TIDAK DAPAT masuk ke jaringan Wi-Fi rumah pribadi Anda** untuk mengakses IP privat TV Anda (`192.168.x.x`) karena firewall NAT router rumah Anda.
* **Solusi:** Perintah ADB harus dijalankan oleh perangkat lokal di Wi-Fi rumah Anda (seperti laptop/PC yang menjalankan Google Antigravity atau Claude Code, atau ponsel Android yang menjalankan Bugjaeger).

---

### Q2: Apakah ada AI yang bisa memasang dan mengerjakan semua tugas langsung tanpa pengguna memasang APK secara manual?
* **YA, jika dijalankan di komputer/laptop pada Wi-Fi yang sama (Google Antigravity / Claude Code / Cursor):**
  AI terhubung ke TV melalui ADB nirkabel (`adb connect 192.168.x.x`), membangun APK, memasangnya, menghapus bloatware, dan memverifikasi semuanya dengan tangkapan layar otomatis. Pengguna tidak perlu menyentuh atau membuka file APK secara manual!
* **Di ponsel tanpa PC:** Anda cukup menggunakan aplikasi bantuan (Bugjaeger di Android, atau LocalSend di iPhone) untuk mentransfer dan memasang APK.

---

### Q3: Jika pengguna hanya memasang file APK, apakah mereka punya akses penuh dengan remote TV, atau masih perlu akses AI?
* **100% MANDIRI! ANDA HANYA MEMERLUKAN REMOTE TV, TANPA AI!**
* Glass TV Home adalah **aplikasi Android TV native** (Kotlin/AndroidX native).
* Setelah APK terpasang di TV:
  - **Kontrol Remote Penuh**: Tombol D-Pad (Atas/Bawah/Kiri/Kanan/OK), Kembali, dan Home menavigasi semuanya secara native.
  - **Semua fitur pintar berjalan lokal**: Jam, tanggal Hijriah, cuaca & AQI (APIMS), screensaver 10 menit, dan tidur otomatis 2 jam beroperasi sepenuhnya di dalam TV tanpa memerlukan agen AI, komputer, atau langganan aktif.
  - Anggota keluarga dan pengguna non-teknis dapat menggunakan TV seperti smart TV komersial standar.
* **Peran AI murni sebagai "mekanik/teknisi"** selama penyiapan awal untuk mengotomatiskan pemasangan dan debloat. Setelah terpasang, Glass TV Home 100% independen.

---

### Q4: Bisakah saya menggunakan ini di Unifi TV Box (TM Unifi Plus Box), dan apakah garansi saya batal?
* **1. Bisakah dipasang di Unifi TV Box?**
  - **YA, TENTU SAJA!** TM Unifi Plus Box (baik model putih Skyworth HP40A3 maupun model hitam ZTE) menjalankan Android TV resmi.
  - Anda dapat memasang APK Glass TV Home melalui flash drive USB, aplikasi Downloader, Send Files to TV, atau ADB nirkabel.
  - **Aplikasi unifi TV resmi berfungsi normal:** Anda tidak akan kehilangan akses ke saluran langsung, olahraga, atau acara on-demand TM karena aplikasi **unifi TV** akan otomatis muncul di daftar aplikasi Anda dan dapat disematkan ke baris dock favorit Anda.
* **2. Apakah ini membatalkan garansi TM?**
  - **TIDAK, GARANSI ANDA TETAP 100% AMAN DAN UTUH.**
  - **Tanpa Root / Tanpa Modifikasi Firmware:** Glass TV Home beroperasi sebagai aplikasi ruang pengguna Android standar (sama seperti memasang Netflix atau YouTube). Aplikasi ini tidak mengubah partisi firmware sistem atau merusak perangkat keras.
  - **100% Dapat Dibatalkan:** Jika Anda memerlukan dukungan teknis dari TM atau ingin mengembalikan box, cukup buka:
    `Settings -> Apps -> Glass TV Home -> Uninstall`
    atau lakukan Factory Reset standar. Box langsung kembali ke launcher pabrik TM asli tanpa jejak yang tertinggal.

---

## 📄 Lisensi & Kredit
* Dibangun di atas [CleanYourTV](https://github.com/Gathin23/cleanyourtv) © Gathin (Lisensi MIT).
* Font Outfit dengan SIL Open Font License.
* Data cuaca dan AQI didukung oleh Open-Meteo & standar Department of Environment Malaysia (APIMS).
