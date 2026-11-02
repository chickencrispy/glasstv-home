# Panduan Pengguna Glass TV Home (Untuk Pemula) 📺✨

Selamat datang di **Glass TV Home**! Panduan ini khusus untuk pengguna baru (*ramah pemula*) agar Anda dapat memasang, mengoptimalkan TV supaya cepat tanpa iklan, menggunakan remote TV, dan menikmati semua fitur menarik dengan mudah tanpa pusing kepala.

---

## 🌟 1. Apa Itu Glass TV Home?

Glass TV Home adalah pengganti layar utama (*launcher*) untuk Android TV dan Google TV (seperti Xiaomi Mi Box S, Mi TV Stick, Android TV Sharp, Sony, TCL, Philips, Mecool, dll.):
* 🚀 **Tanpa Iklan & Sangat Ringan**: Hanya berukuran sekitar 570 KB, lancar dan tidak membebani TV (menghemat lebih dari 400 MB RAM dibandingkan layar bawaan).
* 🪟 **Gaya Kaca Frosted (tvOS)**: Desain modern seperti Apple TV dengan bayangan kaca mengambang dan susunan aplikasi yang rapi.
* ⛅ **Dashboard Cuaca & Kabut Asap (API)**: Pemeriksaan suhu saat ini, prakiraan 12 jam, 7 hari, serta indeks pencemaran udara resmi (APIMS Jabatan Alam Sekitar).
* 🌙 **Screensaver Cerdas & Hemat Listrik**: Masuk screensaver setelah 10 menit diam, dan TV mati otomatis (*sleep standby*) setelah 2 jam.

---

## 📦 Unduh File APK Langsung (Direct Download)

Bagi pengguna yang ingin mengunduh file APK siap pasang secara langsung:
* 📥 **Unduh File APK Resmi**: [**glass-tv-home.apk**](https://github.com/chickencrispy/glasstv-home/raw/main/release/glass-tv-home.apk)
* 📁 **Lokasi di Dalam Repositori**: [`release/glass-tv-home.apk`](release/glass-tv-home.apk)
* ⚡ **Versi**: **v3.3.1-id** (Edisi Indonesia: 🍏 Dashboard Cuaca gaya Apple Weather Bento Grid, 🌧️ Latar Belakang Cuaca Dinamis, 📡 Peta Radar Curah Hujan Doppler, 🧭 Kompas Angin Berputar, 🌙 Fase Bulan 3D, 🎮 Profil Suara Konsol: PS5, Switch, Xbox & Mode Senyap, 🔄 Pembaruan Otomatis In-App OTA).

> [!NOTE]
> ### 🇲🇾 Pengguna Unifi TV Box (TM Unifi Plus Box): Apakah Bisa Digunakan & Apakah Ini Membatalkan Garansi?
> * **Apakah bisa digunakan? YA, 100% BISA!** Unifi Plus Box (model Skyworth HP40A3, ZTE B866V2K, dan kotak Android TV baru TM) menggunakan sistem operasi Android TV yang sah (*certified Android TV*). Aplikasi resmi **unifi TV** (siaran langsung & saluran berlangganan Anda) tetap ada dalam daftar aplikasi dan bisa ditonton seperti biasa.
> * **Apakah ini membatalkan garansi (void warranty)? TIDAK SAMA SEKALI!** Glass TV Home dipasang sebagai aplikasi biasa (*user-space APK*). Ia **TIDAK melakukan root (no root)** pada kotak TV Anda, **TIDAK membuka bootloader**, dan **TIDAK mengubah sistem perangkat lunak internal TM (firmware)**. Anda bisa *uninstall* kapan saja melalui menu Pengaturan TV untuk kembali ke tampilan asli TM tanpa dampak apa pun.

---

## 🤖 2. Cara Paling Mudah: Bantuan AI (Hanya 1 Baris Prompt!)

Anda **tidak perlu tahu coding** atau mengetik perintah teknis yang panjang. Setelah mengaktifkan mode pengembang di TV, Anda hanya perlu menempelkan **satu baris prompt** ini saja kepada asisten AI mana pun (*AI Coding Assistant* seperti Google Antigravity gratis atau Claude Code):

```text
install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
```

AI akan mengurus **SEMUANYA** secara otomatis dari awal hingga selesai!

### 🔄 Bagi Pengguna yang Sudah Memasang: Prompt Pembaruan Patch Terbaru (Update Patch)
Jika Anda sudah memasang Glass TV Home dan ingin memperbarui ke versi atau patch terbaru tanpa pusing kepala:
```text
update patch latest glass tv dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
```
*(Atau: `kemaskini glass tv dekat tv saya: https://github.com/chickencrispy/glasstv-home`)*

AI akan menyambung ke TV melalui Wi-Fi, memasang pembaruan (`adb install -r`), dan membuka kembali aplikasi dalam waktu 5 detik. **Semua gambar wallpaper, susunan aplikasi di dock, dan pilihan suara Anda akan 100% tetap!**

### 💡 Pilihan Asisten AI:
1. **Google Antigravity (Gratis / Free)** ⭐ *Paling Direkomendasikan*:
   - Agen AI resmi dari Google DeepMind dengan akses terminal shell penuh, alat otomasi, dan kemampuan menjalankan skrip secara gratis.
2. **Claude Code (Berbayar / Subscription Anthropic)**:
   - Agen coding CLI bertenaga dari Anthropic yang berjalan langsung di terminal komputer Anda.
3. **Cursor / Windsurf IDE (Freemium / Berbayar)**:
   - Editor kode pintar berbasis AI yang memiliki terminal shell terintegrasi.

---

### 📋 Langkah 1: Aktifkan Developer Mode & Wireless Debugging di TV Anda
Sebelum AI dapat menyambung ke TV melalui Wi-Fi, lakukan 2 pengaturan sederhana ini pada TV menggunakan remote:
1. Pada remote TV Anda, buka **Settings (Pengaturan / ikon gear ⚙️)**.
2. Pilih **Device Preferences** (atau **System** pada Google TV baru).
3. Pilih **About (Tentang)** $\rightarrow$ Gulir ke baris bertuliskan **Build** (atau *Android TV OS Build*).
4. **Tekan tombol OK pada remote sebanyak 7 kali berturut-turut** hingga muncul pesan: *"You are now a developer!"*.
5. Tekan tombol *Back* sekali. Masuk ke menu baru yaitu **Developer options (Pilihan pengembang)**.
6. Cari dan **AKTIFKAN**:
   - ✅ **USB debugging** (Ubah ke ON/Aktif).
   - ✅ **Wireless debugging / Network debugging** (Ubah ke ON/Aktif).
7. Pastikan TV dan komputer/ponsel Anda tersambung pada **jaringan Wi-Fi yang sama**.

---

### 📋 Langkah 2: Tempelkan Prompt 1 Baris Ini kepada AI
Buka **Google Antigravity**, **Claude Code**, atau **Cursor** di laptop/komputer Anda. Salin dan tempelkan satu baris ini saja:

```text
install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
```

> **Apa yang akan dilakukan AI secara otomatis setelah Anda mengirim prompt ini?**
> 1. AI akan menanyakan alamat IP TV Anda (atau mendeteksi IP TV di jaringan Wi-Fi Anda secara otomatis).
> 2. AI akan menanyakan di mana lokasi Anda di Malaysia (mis.: Putrajaya, KL, Cheras, Melaka, JB, Penang, Kuantan, dll.) untuk menyesuaikan stasiun kabut asap JAS APIMS.
> 3. AI menyambung ke TV Anda melalui Wi-Fi via ADB (`adb connect <ip>:5555`).
> 4. AI membuat salinan cadangan (*backup*) sistem asli TV.
> 5. AI mematikan aplikasi bloatware pabrikan yang memperlambat TV, mematikan iklan bersponsor, dan mengaktifkan AdGuard Private DNS untuk memblokir pelacak sistem.
> 6. AI memasang file APK Glass TV Home yang sangat ringan (di bawah 600 KB) sebagai layar utama.
> 7. AI mengambil tangkapan layar (*screenshot*) TV Anda yang kini cepat dan bersih untuk konfirmasi Anda!

*(Catatan: Jika Anda ingin memberikan informasi lengkap langsung dalam satu pesan tanpa AI bertanya lagi, Anda juga bisa menggunakan format terperinci ini:)*
```text
install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home
Model TV: [cth: Xiaomi Mi Box S]
IP TV: [cth: 192.168.68.102]
Lokasi Saya: [cth: Putrajaya / Cheras / Shah Alam / Johor Bahru / Penang]
```

> [!IMPORTANT]
> **🚨 Pesan Khusus Untuk Asisten AI (AI Assistant Rule)**:
> Ketika pengguna memberikan prompt `install ni dekat tv rumah saya: https://github.com/chickencrispy/glasstv-home`, AI **WAJIB** bertanya kepada pengguna:
> 1. *"Apa alamat IP TV Anda di menu Settings -> Network & Internet (atau merek TV Anda)?"* (jika belum dapat dideteksi secara otomatis)
> 2. *"Di manakah lokasi Anda di Malaysia (contoh: Putrajaya, Cheras, Shah Alam, Melaka, Johor Bahru, Pulau Pinang, Kuantan, Kuching, Kota Kinabalu, dll.) agar data kabut asap/IPU resmi Jabatan Alam Sekitar (JAS) dapat diselaraskan secara otomatis?"*
> 
> Setelah tersambung ke TV, AI bisa langsung memasang APK resmi yang sudah ada dari `release/glass-tv-home.apk` atau mengompilasi APK baru, menjalankan debloat, dan menetapkan lokasi pengguna via:
> ```bash
> adb shell am broadcast -a com.gathin.tvhome.SET_LOCATION -p com.gathin.tvhome --es query "<NAMA_LOKASI>"
> ```
> *(Jika pengguna tidak menyebutkan lokasi, sistem akan menggunakan lokasi bawaan: **Putrajaya** / Stasiun CA17W).*

---

### 📋 Langkah 3: Tekan "Allow" di Layar TV
Ketika AI mulai menyambung ke TV, jendela konfirmasi akan muncul di layar TV Anda:
* *"Allow USB debugging from this computer?"*
* Centang kotak: ☑ **"Always allow from this computer"**
* Pilih dan tekan tombol **OK / Allow**.

AI akan mengurus sisanya: menghapus bloatware yang memperlambat TV, mengaktifkan pemblokir iklan, memasang Glass TV Home, dan menampilkan tangkapan layar (*screenshot*) TV Anda yang kini sangat cepat dan bersih!

---

### ↩️ Cara Kembali ke Asal (Undo / Restore)
Semua perubahan yang dibuat oleh AI **100% aman dan dapat dikembalikan**:
* AI tidak menghapus file sistem secara permanen, hanya menonaktifkannya.
* AI secara otomatis menghasilkan skrip pemulihan (`restore.sh` / `restore.ps1`).
* Jika Anda ingin kembali ke layar asli pabrikan di masa mendatang, Anda hanya perlu memberi tahu AI:
  > *"Tolong restore kembali TV saya ke pengaturan dan launcher asli pabrik."*
  Semua aplikasi asli akan dipulihkan seketika seperti semula.

---

## 🎮 3. Cara Mengontrol dengan Remote TV (D-Pad)

Sangat mudah dan intuitif! Anda hanya perlu tombol arah pada remote TV Anda:

| Tombol Remote | Tindakan / Fungsi |
| :--- | :--- |
| **Tombol HOME (Sekali Tekan)** | Kembali ke layar utama dari aplikasi mana pun. Jika di layar utama, buka screensaver StandBy. |
| **Tekan & Tahan HOME (Long-Press)** | Buka Menu Pengaturan Kaca Mengambang (*Quick Settings Popup*) secara global dari aplikasi mana pun (termasuk Netflix, YouTube, dll.). |
| **Tombol ATAS (▲)** | Naik ke atas untuk memilih **Kapsul Cuaca & Kabut Asap**, menu Inputs, Wi-Fi, atau **Tombol Pengaturan (⚙️ Settings)**. |
| **Tombol TENGAH (OK)** | • Tekan pada aplikasi untuk membuka aplikasi tersebut.<br>• Tekan pada kapsul cuaca untuk membuka **Dashboard Cuaca & Kualitas Udara penuh**.<br>• Tekan pada tombol **Settings (⚙️)** untuk membuka **Menu Dropdown Kaca** (Wallpaper, Kabut Asap, Android Settings).<br>• **Tekan & Tahan (Long-press)** pada aplikasi untuk mengatur atau memindahkan ikon ke dalam/luar dock. |
| **Tombol BAWAH (▼)** | Turun kembali ke barisan aplikasi utama (*Dock*). |
| **Tombol KIRI / KANAN (◀ / ▶)** | Bergerak di antara aplikasi atau beralih di antara menu atas. |
| **Tombol BACK (↩)** | Keluar dari Dashboard Cuaca, Dialog Pilihan, atau Screensaver dan kembali ke layar utama. |

---

## 📱 4. Penjelasan Fitur-Fitur Utama

### A. Kapsul Cuaca & Indeks Kabut Asap (Bawah Jam)
* Terletak tepat di bawah jam utama (contoh: `☁ 27° Overcast  •  API 106 Unhealthy`).
* **Tekan tombol Atas (▲) lalu tekan OK** untuk membuka layar cuaca penuh:
  1. **Suhu Saat Ini & Angin**: Suhu, suhu terasa (*feels like*), kelembapan, kecepatan angin, indeks UV, waktu matahari terbit & terbenam.
  2. **Kualitas Udara Resmi (JAS APIMS / IPU)**: Menggunakan data resmi stasiun Jabatan Alam Sekitar Malaysia (MyEQMS/APIMS) dengan bilah warna standar resmi Malaysia (Baik: Hijau, Sedang: Biru Langit, Tidak Sehat: Jingga, Sangat Tidak Sehat: Merah, Berbahaya: Maroon) dan saran kesehatan sesuai tingkat IPU saat ini.
  3. **Prakiraan 12 Jam**: Barisan kartu per jam dengan suhu dan persentase kemungkinan hujan (`↑ %`).
  4. **Prakiraan 7 Hari**: Tinjauan cuaca sepanjang minggu dengan suhu maksimum/minimum.

📍 **Cara Mengganti Lokasi Stasiun Kabut Asap Secara Manual Menggunakan Remote TV (Jika Memasang APK Sendiri):**
Pengguna yang memasang file APK secara manual atau pindah rumah **TIDAK MEMERLUKAN KOMPUTER ATAU AI** untuk mengganti lokasi:
1. Pada layar utama, tekan tombol **Atas (▲)** dan tekan **OK** pada kapsul cuaca untuk membuka Dashboard Cuaca.
2. Tekan tombol **Atas (▲)** sekali lagi ke tombol **📍 Ganti Stasiun / Change** (terletak di samping nama kota).
3. Tekan **OK**. Menu pilihan daftar stasiun resmi JAS Malaysia akan muncul:
   - *Putrajaya, Cheras (KL), Batu Muda (KL), Petaling Jaya, Shah Alam, Klang, Banting, Bandaraya Melaka, Alor Gajah, Bukit Rambai, Seremban, Nilai, Port Dickson, Larkin (JB), Pasir Gudang, Batu Pahat, Kluang, Tangkak, Seberang Jaya (Penang), Minden (USM), Balik Pulau, Ipoh, Taiping, Seri Manjung, Alor Setar, Sungai Petani, Langkawi, Kangar, Kuantan, Temerloh, Kuala Terengganu, Kemaman, Kota Bharu, Kota Kinabalu, Sandakan, Tawau, Kuching, Miri, Bintulu, Sibu, Labuan*.
4. Gunakan tombol **Atas / Bawah** remote untuk memilih stasiun Anda, lalu tekan **OK**.
5. Seketika pembacaan Indeks Pencemaran Udara (JAS IPU) akan diperbarui sesuai kota pilihan Anda!

### B. Gambar Latar Belakang & Pilihan Kategori Wallpaper (4K Dynamic)
* **Koleksi Tema Beragam**: Kini Anda dapat memilih kategori tema wallpaper favorit Anda langsung dari layar utama melalui menu Pengaturan (ikon ⚙️):
  - 🌿 **Natural (Bawaan / Bing Places)**: Lanskap alam dengan kualitas 4K lengkap dengan info nama lokasi, negara, dan penjelasan singkat setiap minggu.
  - 🇲🇾 **Malaysia (Koleksi per Negara Bagian)**: Pilih koleksi menurut salah satu dari 14 negara bagian di Malaysia atau putar semuanya sekaligus melalui sub-dialog khusus:
    - 📍 **Johor**: Pulau Rawa Mersing & Pesisir Pantai Tropis.
    - 📍 **Kedah**: Langkawi Sky Bridge Gunung Mat Cincang & Pulau Langkawi.
    - 📍 **Kelantan**: Pantai Cahaya Bulan & Deretan Rhu Pantai Senok Kota Bharu.
    - 📍 **Melaka**: Kota Bersejarah UNESCO & Pesisir Sungai Melaka Malam.
    - 📍 **Negeri Sembilan**: Pantai Cahaya Negeri & Wisata Teluk Kemang Port Dickson.
    - 📍 **Pahang**: Hamparan Ladang Teh Cameron Highlands & Lembah Teh Bharat.
    - 📍 **Perak**: Kota Warisan Kolonial Ipoh, Istana Kellie's Castle Batu Gajah & Gua Kek Lok Tong.
    - 📍 **Perlis**: Gua Kelam Kaki Bukit & Pegunungan Batu Kapur Nakawan.
    - 📍 **Pulau Pinang**: Lorong Warisan UNESCO Georgetown & Kuil Kek Lok Si Air Itam.
    - 📍 **Sabah**: Puncak Gunung Kinabalu & South Peak Kundasang Negeri Di Bawah Bayu.
    - 📍 **Sarawak**: Pinnacles Batu Kapur Mulu UNESCO, Tepian Kuching Waterfront & Pantai Miri.
    - 📍 **Selangor**: Tangga Batu Caves Sri Subramaniar & Masjid Sultan Salahuddin Abdul Aziz Shah Alam.
    - 📍 **Terengganu**: Pulau Perhentian Surga Marin & Masjid Kristal Muara Sungai Terengganu.
    - 📍 **Wilayah Persekutuan**: Menara Kembar Petronas KLCC & Horizon Malam Kota Kuala Lumpur.
  - 🏙️ **Skyscraper (Pencakar Langit)**: Pemandangan menara ikonik dunia (New York Manhattan, Chicago River, Dubai Marina, Hong Kong Cityscape).
  - 🌌 **Deep Space (Antariksa)**: Galaksi, nebula dan bintang angkasa yang menakjubkan.
  - 🌊 **Ocean & Coastal (Samudra)**: Keindahan pantai pasir putih, ombak dan terumbu karang.
  - 🔲 **Minimalist Dark**: Latar belakang gelap bertekstur elegan, sangat hemat energi dan ramah panel OLED.
* **📂 Sub-Dropdown Pemilih Negara Bagian (UI Rapi)**:
  - Tekan **`🇲🇾 Malaysia (Pilih Negara Bagian ▶)`** pada menu wallpaper untuk membuka daftar negara bagian yang rapi dengan standar Leanback TV.
  - Pilihan Anda ditampilkan langsung pada menu utama (contoh: `🇲🇾 Malaysia: Johor (Ganti Negara Bagian ▶)`).
  - Di sudut kiri layar, nama lokasi, negara bagian, dan sejarah tempat tersebut ditampilkan secara otomatis!
* **📲 Unggah Gambar dari Ponsel (Pindai Kode QR)**:
  - Pilih **`📲 Upload Wallpaper via Phone (QR Code)`** pada menu wallpaper untuk menampilkan kode QR dan tautan web lokal.
  - Pindai dengan ponsel pintar Anda (pada Wi-Fi yang sama) untuk mengunggah foto keluarga, hewan peliharaan, atau gambar favorit Anda langsung ke TV tanpa memerlukan aplikasi pihak ketiga atau akun cloud.
* **📱 Pengelola Gambar TV & Mode 1 Gambar Permanen (Single Mode)**:
  - Setelah mengunggah gambar, Anda dapat mengelola gambar baik melalui ponsel atau langsung di TV melalui menu **`🗑️ Kelola Gambar yang Diunggah (Pilih / Hapus)`**.
  - **Kunci 1 Gambar Saja**: Pilih **`📌 Jadikan Wallpaper Tunggal`** jika Anda ingin menetapkan hanya 1 gambar permanen tanpa berganti-ganti.
  - **Putar Semua Gambar**: Pilih **`🔄 Putar Semua Gambar`** untuk memutar semua foto yang diunggah secara otomatis.
  - **Hapus Foto**: Hapus foto tertentu atau kosongkan semua gambar sekaligus.
* **🖼️ Tampilan Bersih (Clean View)**:
  - Saat menggunakan wallpaper sendiri (*custom*), deretan teks deskripsi di sebelah kiri layar dimatikan sepenuhnya (`View.GONE`) agar gambar Anda ditampilkan dengan jelas tanpa terhalang.
* **Ganti Gambar Seketika**: Pilih **🔀 Next Wallpaper** untuk beralih ke gambar berikutnya kapan saja.

### C. Screensaver StandBy & Auto-Off 2 Jam (Hemat Listrik)
* **10 Menit Dibiarkan Tanpa Tombol**: TV akan otomatis masuk mode *StandBy Screensaver* berlatar hitam pekat (aman untuk mencegah *screen burn-in* pada TV OLED/LED), menampilkan jam besar 110sp, tanggal Hijriah (contoh: `🌙 12 Jumadilawal 1448H`), dan salat berikutnya.
* **2 Jam Setelah Itu**: Jika TV terus tidak disentuh, sistem akan mematikan tampilan TV (*sleep standby*) secara otomatis.
* **Untuk Menghidupkan Kembali**: Tekan tombol apa pun pada remote untuk membangunkan TV.

### D. Menu Pengaturan Frosted Glass (Settings Dropdown Menu) ⚙️
Di bar atas layar utama, tekan tombol **Settings (ikon gear ⚙️)** untuk menampilkan menu mengambang gaya kaca Apple tvOS:
* 🖼️ **Wallpaper**: Membuka jendela pilihan kategori wallpaper (*Natural, Skyscraper, Deep Space, Ocean, Minimalist Dark, Next Wallpaper*). Pilihan Anda disimpan secara permanen meskipun TV dimatikan.
* 📍 **Location API Haze**: Membuka daftar 30+ stasiun kabut asap resmi JAS APIMS untuk mengganti stasiun pemantauan udara di seluruh negara bagian.
* 🔊 **Sound Effect**: Menampilkan tema suara aktif (contoh: `Sound: PlayStation 5`) dan membuka dialog pilihan tema audio konsol (PS5, Nintendo Switch, Xbox Series X/S, atau Bisu/Off) dengan pratinjau nada seketika.
* 🔄 **Check for Updates**: Memeriksa patch atau versi pembaruan baru langsung dari GitHub tanpa memerlukan komputer.
* ⚙️ **All Settings**: Membuka menu pengaturan penuh Android TV OS.
*(Pintasan: Menekan lama tombol Settings pada remote juga akan membuka pengaturan Android secara langsung).*

### E. Pilihan Suara Konsol & Sakelar Bisu (Sound Effects) 🎮🔊
Kini Anda dapat memilih tema efek suara navigasi ala konsol permainan favorit Anda atau mematikannya sepenuhnya:
* **PlayStation 5 (Default)**: Suara pergerakan dan klik ikonik konsol PS5 generasi terbaru yang modern, lembut, dan rapi.
* **Nintendo Switch**: Suara klik mekanis taktil dan 'snap' ceria yang sangat responsif setiap kali tombol ditekan.
* **Xbox Series X/S**: Suara 'blip' elektronik akustik lembut dengan nada harmonik ganda modern yang tenang.
* **Muted / Off (Bisu)**: Matikan semua efek suara navigasi jika Anda lebih menyukai suasana senyap sepi di malam hari.
* **Pratinjau Suara Seketika**: Setiap kali Anda mengklik pilihan di dalam dialog, sistem akan memainkan suara pilihan tersebut seketika sebagai sampel.
* **Tetap Tersimpan**: Pilihan Anda disimpan secara permanen dalam memori TV dan tidak hilang meskipun TV dimatikan atau dinyalakan kembali.

### F. Pemeriksaan & Auto-Update Langsung dari TV (In-App OTA Updater) 🔄🚀
Glass TV Home dilengkapi dengan mesin pembaruan pintar tanpa perlu komputer atau bantuan eksternal:
* **Pemeriksaan Manual**: Tekan **Settings (⚙️) $\rightarrow$ Check for Updates**. Sistem akan langsung terhubung dengan server GitHub untuk memeriksa apakah tersedia versi terbaru.
* **Pemeriksaan Otomatis di Latar Belakang**: Setiap kali aplikasi Glass TV dibuka, ia akan memeriksa secara diam-diam di latar belakang. Jika versi baru ditemukan, dialog pembaruan dengan daftar perubahan (*changelog*) akan ditampilkan.
* **Unduh & Pasang 1-Klik**: Klik tombol **Unduh & Pasang**, TV akan mengunduh file APK langsung ke perangkat dan membuka pemasang resmi Android TV untuk Anda konfirmasi dengan remote.
* **Aman & Permanen**: Proses pembaruan tidak menghapus file data, susunan aplikasi, atau pengaturan pribadi Anda.

---

## 📱 5. Cara Menggunakan Ponsel Pintar (Tanpa Komputer / Laptop Sama Sekali!)

**Sangat bisa!** Jika Anda tidak memiliki komputer atau laptop, Anda dapat menggunakan ponsel pintar Android Anda untuk memasang, mempercepat TV, dan menghapus iklan.

---

### Metode 1: Gunakan Aplikasi 'Bugjaeger' (Paling Ampuh - Pasang APK + Hapus Iklan dari Ponsel)
Aplikasi **Bugjaeger Mobile ADB** (gratis di Google Play Store ponsel) memungkinkan ponsel Anda bertindak seperti komputer untuk mengontrol dan mengoptimalkan TV.

1. **Sambungkan Wi-Fi**: Pastikan ponsel dan TV berada di Wi-Fi yang sama.
2. **Pasang Bugjaeger**: Unduh aplikasi **Bugjaeger** di ponsel Anda dari Play Store.
3. **Sambungkan ke TV**:
   - Buka Bugjaeger, tekan ikon colokan (**Connect 🔌**).
   - Masukkan alamat IP TV Anda (contoh: `192.168.68.102`) dan port `5555`.
   - Tekan **Connect**.
   - Pada layar TV, tekan **Allow** ketika muncul jendela *"Allow USB debugging"*.
4. **Pasang Glass TV Langsung dari Ponsel**:
   - Masuk ke tab **Packages (ikon kotak 📦)** di Bugjaeger.
   - Tekan tombol tambah (**+**) di atas, pilih file `app-release.apk` yang telah diunduh ke ponsel Anda.
   - Bugjaeger akan memasang Glass TV langsung ke TV dalam beberapa detik!
5. **Hapus Iklan & Blokir Tracking dari Ponsel**:
   - Masuk ke tab **Commands (ikon terminal ⚡)** di Bugjaeger.
   - Salin dan tempelkan perintah ini untuk memblokir iklan sistem:
     ```bash
     settings put global private_dns_specifier dns.adguard-dns.com
     settings put global private_dns_mode hostname
     ```
   - Masukkan perintah untuk mematikan iklan pabrikan (mis. Xiaomi PatchWall):
     ```bash
     pm disable-user --user 0 com.mitv.tvhome
     pm disable-user --user 0 com.xiaomi.mitv.advertise
     ```
   - Selesai! TV Anda kini bebas iklan dan sangat cepat.

---

### Metode 2: Paling Sederhana (Hanya Pasang APK Menggunakan 'Send Files to TV')
Jika Anda hanya ingin memasang Glass TV Home tanpa melakukan debloat teknis:
1. Pasang aplikasi gratis **Send Files to TV** di ponsel pintar dan di Android TV Anda (dari Google Play Store).
2. Unduh `app-release.apk` ke ponsel Anda (dari tautan file GitHub).
3. Buka aplikasi pada kedua perangkat, kirim APK dari ponsel ke TV melalui Wi-Fi.
4. Di TV Anda, buka aplikasi pengelola file (seperti *File Commander* atau *FX File Explorer*), klik file APK dan pilih **Install**.
5. Tekan tombol **Home** pada remote TV dan pilih **Glass TV Home** sebagai *Always / Selalu*.

---

### 🤖 Bisakah Bertanya kepada AI dari Ponsel?
Bisa! Anda dapat membuka aplikasi AI di ponsel (seperti Claude, ChatGPT, atau browser web):
1. Beri tahu AI merek TV Anda:
   > *"Saya menggunakan ponsel dan aplikasi Bugjaeger. TV saya model Xiaomi Mi Box S. Berikan saya daftar perintah debloat untuk mematikan iklan dan tracking."*
2. AI akan memberikan daftar perintah `pm disable-user`.
3. Anda hanya perlu menyalin dan menempelkan perintah tersebut ke dalam aplikasi **Bugjaeger** di ponsel Anda!

---

### 🍏 Khusus untuk Pengguna iPhone (iOS): Apakah Bisa Menggunakan iPhone?

**Jawaban jujur: Bisa untuk memasang APK & memblokir iklan, tetapi ada sedikit perbedaan dibandingkan Android.**

Karena kebijakan Apple (iOS) yang ketat, Apple tidak mengizinkan aplikasi seperti *Bugjaeger / ADB* berada di App Store. Namun, pengguna iPhone tetap dapat melakukannya dengan sangat mudah:

#### 1. Cara Mengirim & Memasang APK dari iPhone ke TV (Gunakan 'LocalSend'):
* Unduh aplikasi gratis **LocalSend** di App Store iPhone dan di Google Play Store TV Anda.
* Unduh file `app-release.apk` di iPhone Anda dari tautan GitHub ini.
* Buka LocalSend pada iPhone dan TV (pastikan keduanya tersambung ke Wi-Fi yang sama).
* Kirim APK dari iPhone ke TV dengan 1 klik saja!
* Di TV, klik file APK tersebut dan tekan **Install**.

#### 2. Cara Memblokir Iklan & Tracking Jika Menggunakan iPhone (Tanpa Perlu ADB):
Anda tidak memerlukan aplikasi ADB pun untuk memblokir iklan. Anda dapat melakukannya langsung melalui remote TV:
1. Pada remote TV, pergi ke **Settings ⚙️** $\rightarrow$ **Network & Internet**.
2. Pilih **Private DNS** (ubah dari *Off/Automatic* ke *Private DNS provider hostname*).
3. Masukkan alamat ini menggunakan papan ketik di TV:
   ```text
   dns.adguard-dns.com
   ```
4. Tekan **Save / Simpan**.
5. Seketika semua iklan banner, pelacak data pabrikan, dan analitik diblokir di tingkat sistem TV Anda secara otomatis!

#### 3. Ingin Menghapus Bloatware Pabrikan Secara Penuh?
Jika Anda ingin menghapus aplikasi bawaan pabrikan (seperti Xiaomi PatchWall) hingga 100% bersih, Anda disarankan menggunakan **laptop/komputer** (atau meminjam ponsel Android teman sekali saja selama 2 menit). Setelah dihapus sekali, ia akan tetap bersih selamanya di TV Anda!

---

## 🛠️ 6. Cara Pasang Manual Menggunakan Flash Drive (Alternatif)

Jika Anda lebih suka menggunakan flash drive USB:
1. Unduh file [**glass-tv-home.apk**](https://github.com/chickencrispy/glasstv-home/raw/main/release/glass-tv-home.apk) (tersedia dalam folder [`release/`](release/glass-tv-home.apk)).
2. Salin file `glass-tv-home.apk` tersebut ke dalam flash drive USB.
3. Colokkan flash drive ke Android TV atau Xiaomi Mi Box Anda.
4. Buka aplikasi pengelola file di TV (mis.: *File Commander* atau *FX File Explorer* dari Google Play Store TV).
5. Klik `glass-tv-home.apk` dan tekan **Install**.
   *(Jika TV meminta izin "Install unknown apps", pilih **Allow/Izinkan**)*.
6. Setelah selesai dipasang, tekan tombol **Home** pada remote TV dan pilih **Glass TV Home** sebagai *Always / Selalu*.

---

## ❓ 7. Pertanyaan Umum (FAQ) Mengenai AI, Ponsel & Remote TV

### S1: Bisakah menjalankan Claude Code di ponsel (iOS / Android) dan apakah masalah iOS Sandbox menghalangi akses network?
* **Realitas Claude Code di Ponsel:** Claude Code adalah perangkat lunak Terminal/CLI (`@anthropic-ai/claude-code`) yang memerlukan mesin **Node.js 18+**. Di iPhone (iOS), sistem keamanan Sandbox Apple tidak mengizinkan eksekusi mesin Node.js atau shell secara langsung di dalam ponsel.
* **Masalah Akses Jaringan (Cloud vs Local Network):** Claude Code (atau AI cloud mana pun seperti Claude.ai / ChatGPT) berjalan di server cloud. Server cloud di internet **TIDAK DAPAT menembus Wi-Fi rumah Anda** untuk mencapai IP pribadi TV (`192.168.x.x`) karena diblokir oleh *firewall router* rumah Anda.
* **Solusi:** Perintah ADB harus dijalankan dari perangkat yang berada di dalam jaringan Wi-Fi rumah yang sama:
  - **Paling mudah:** Gunakan laptop / komputer (Google Antigravity gratis atau Claude Code di terminal laptop).
  - **Gunakan ponsel pintar:** Pengguna Android dapat menggunakan aplikasi lokal seperti **Bugjaeger Mobile ADB**.

---

### S2: Apakah AI dapat memasang dan melakukan semua tugas secara langsung tanpa pengguna perlu memasang APK sendiri?
* **YA, jika menggunakan Komputer / Laptop (Google Antigravity / Claude Code / Cursor):**
  AI pada komputer dapat tersambung langsung ke TV melalui Wi-Fi rumah (`adb connect 192.168.x.x`). AI akan membangun APK $\rightarrow$ mengirim ke TV $\rightarrow$ memasang APK $\rightarrow$ menghapus iklan pabrikan $\rightarrow$ dan menguji layar secara otomatis 100%. Anda tidak perlu mengunduh atau menyentuh file APK sama sekali!
* **Jika Hanya Menggunakan Ponsel:** Anda memerlukan bantuan aplikasi perantara lokal (Android menggunakan *Bugjaeger*, iPhone mengirim file melalui *LocalSend*).

---

### S3: Jika pengguna hanya memasang file APK, apakah bisa akses penuh menggunakan remote TV atau perlu akses AI juga?
* **100% BISA MENGGUNAKAN REMOTE SAJA, TIDAK MEMERLUKAN AI LAGI!**
* Glass TV Home dibangun sebagai aplikasi Android TV asli (*Native Android TV app*).
* Segera setelah file APK terpasang di TV:
  - **Remote TV berfungsi sepenuhnya**: Tombol D-Pad (Atas/Bawah/Kiri/Kanan/OK) untuk mengontrol aplikasi dan cuaca, serta tombol Home/Back berfungsi seperti biasa.
  - **Semua fitur otomatis berjalan sendiri**: Jam, cuaca & kualitas udara (APIMS), mode screensaver 10 menit, dan auto-sleep 2 jam dihasilkan sepenuhnya oleh chip prosesor TV tanpa bergantung pada AI atau internet luar mana pun.
  - Anggota keluarga dan pengguna biasa dapat menikmati TV dengan lancar hanya menggunakan remote TV biasa.
* **Peran AI hanyalah seperti "teknisi"** yang membantu memasang dan membersihkan bloatware pada tahap awal saja.

---

### S4: Saya menggunakan Unifi TV Box (TM Unifi Plus Box), bisakah saya menggunakan launcher ini dan apakah ini akan membatalkan garansi (void warranty)?
* **1. Bisakah dipasang pada Unifi TV Box?**
  - **YA, SANGAT BISA!** Kotak TM Unifi Plus Box (baik model putih Skyworth HP40A3 maupun model hitam ZTE) beroperasi di atas sistem Android TV resmi Google.
  - Anda dapat memasang file APK Glass TV Home baik melalui flash drive USB, aplikasi *Downloader / Send Files to TV*, atau perintah Wi-Fi ADB.
  - **Aplikasi unifi TV tetap berfungsi sepenuhnya:** Anda tidak akan kehilangan siaran TV langsung (*Live TV*), olahraga, atau acara favorit karena aplikasi **unifi TV** akan terdaftar otomatis di layar utama Glass TV dan dapat diletakkan pada dock depan (*favorites*).
* **2. Apakah ini membatalkan garansi TM (Void Warranty)?**
  - **TIDAK, GARANSI ANDA 100% TETAP AMAN.**
  - **Tanpa Root (Zero Root / No Modding):** Glass TV Home hanyalah sebuah aplikasi *launcher* biasa (sama seperti memasang Netflix, YouTube, atau Spotify dari Play Store). Ia tidak mengubah file sistem induk (*firmware / vendor partition*).
  - **Dapat Dihapus Kapan Saja (Reversible):** Jika Anda perlu mengembalikan kotak kepada TM atau mengklaim garansi jika rusak, Anda hanya perlu pergi ke:
    `Settings (Pengaturan) -> Apps (Aplikasi) -> Glass TV Home -> Uninstall (Hapus)`
    atau lakukan *Factory Reset*. Kotak Unifi TV akan kembali 100% ke keadaan asli pabrik tanpa meninggalkan bekas modifikasi apa pun.

---

## 📄 Lisensi & Penghargaan
* Dibangunkan berdasarkan [CleanYourTV](https://github.com/Gathin23/cleanyourtv) © Gathin (Lisensi MIT).
* Font Outfit oleh SIL Open Font License.
* Data cuaca oleh Open-Meteo & data kualitas udara berdasarkan standar APIMS Jabatan Alam Sekitar Malaysia.
