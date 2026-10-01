# Ram Keripik

Aplikasi Android sederhana untuk tugas **Pertemuan 3**: ViewBinding, Intent antar-Activity, custom font, dan penataan layout XML.

Halaman utama berisi tombol **My Project**. Kalau tombol ditekan, aplikasi membuka halaman detail **Keripik Singkong** yang berisi gambar, deskripsi singkat, dan tabel komposisi gizi per 100 g.

## Spesifikasi

| Item | Nilai |
|---|---|
| Bahasa | Kotlin |
| UI | XML Views (template Empty Views Activity) |
| Package | `com.example.ramkeripik` |
| minSdk | 24 |
| compileSdk / targetSdk | 37 |
| Android Gradle Plugin | 9.3.2 |
| Gradle Wrapper | 9.5.0 |
| Java | 11 (source/target compatibility) |

## Materi Pertemuan 3 yang diterapkan

- **ViewBinding** diaktifkan lewat `buildFeatures { viewBinding = true }` di `app/build.gradle.kts`. Tidak ada `findViewById` di project ini.
- **Intent**: `binding.btnMyProject.setOnClickListener` membuat `Intent(this, SnackDetailActivity::class.java)` lalu memanggil `startActivity(intent)`.
- **Activity baru**: `SnackDetailActivity` didaftarkan di `AndroidManifest.xml`.
- **Custom font**: `res/font/montserrat_bold.ttf` dipakai lewat `android:fontFamily="@font/montserrat_bold"`.
- **Resource terpusat**: semua teks ada di `strings.xml` dan semua warna ada di `colors.xml`.
- **Atribut tampilan**: `layout_margin`, `padding`, `textSize`, `textColor`, `backgroundTint`, `gravity`, `fontFamily`, `elevation`.
- **ScrollView** membungkus halaman detail. Tabel gizi dibuat dengan `TableLayout`.

## Struktur file penting

```
ram-keripik/
├── settings.gradle.kts             # nama project + modul :app
├── build.gradle.kts                # build file root
├── gradle.properties
├── gradle/
│   ├── libs.versions.toml          # version catalog (AGP, library)
│   └── wrapper/                    # Gradle Wrapper (jar + properties)
├── gradlew, gradlew.bat
└── app/
    ├── build.gradle.kts            # konfigurasi modul app + viewBinding
    └── src/main/
        ├── AndroidManifest.xml     # MainActivity (launcher) + SnackDetailActivity
        ├── java/com/example/ramkeripik/
        │   ├── MainActivity.kt         # tombol My Project → Intent
        │   └── SnackDetailActivity.kt  # halaman detail (ViewBinding)
        └── res/
            ├── layout/
            │   ├── activity_main.xml          # Button btnMyProject
            │   └── activity_snack_detail.xml  # judul, gambar, deskripsi, tabel gizi
            ├── font/montserrat_bold.ttf       # font Montserrat Bold
            ├── drawable/
            │   ├── keripik.png                # foto keripik singkong
            │   └── bg_image_frame.xml         # bingkai sudut membulat untuk gambar
            └── values/
                ├── strings.xml   # semua teks
                ├── colors.xml    # semua warna
                └── themes.xml    # tema aplikasi
```

## Tabel gizi (per 100 g)

| Komponen | Jumlah |
|---|---|
| Energi | 509 kkal |
| Lemak Total | 30 g |
| Karbohidrat | 57 g |
| Protein | 2 g |
| Serat | 3 g |
| Natrium | 380 mg |

Nilai ini adalah perkiraan umum untuk keripik singkong goreng dan dipakai sebagai contoh tampilan.

## Membuka project di Android Studio

1. Buka Android Studio, lalu pilih **File → Open** (atau **Open** di layar Welcome).
2. Pilih folder `D:\android-ramos\ram-keripik`, lalu klik **OK**.
3. Tunggu proses **Gradle Sync** sampai selesai (lihat progress bar di bagian bawah). Sync pertama bisa agak lama karena perlu mengunduh dependency.
4. Kalau muncul peringatan SDK Platform 37 belum terpasang, buka **Tools → SDK Manager**, centang **Android API 37**, lalu klik **Apply**.

## Menjalankan di emulator

1. Buka **Tools → Device Manager**.
2. Kalau belum punya emulator, klik **+ → Create Virtual Device**. Pilih perangkat (misalnya Pixel 8) dan system image API 24 atau lebih baru (disarankan versi terbaru), lalu klik **Finish**.
3. Pilih emulator di dropdown perangkat pada toolbar, lalu pastikan konfigurasi run yang terpilih adalah **app**.
4. Klik tombol **Run ▶** (atau tekan `Shift + F10`).
5. Setelah aplikasi terbuka, tekan tombol **My Project**. Halaman **Keripik Singkong** akan muncul. Geser ke bawah untuk melihat tabel gizi.

## Build lewat terminal

Dari folder project:

```powershell
.\gradlew.bat assembleDebug
```

Hasil APK ada di `app/build/outputs/apk/debug/app-debug.apk`. Untuk memasang APK ke emulator atau HP yang sedang terhubung:

```powershell
.\gradlew.bat installDebug
```

## Atribusi aset

- **Gambar `keripik.png`**: "Keripik singkong balado cassava chips" oleh Sakurai Midori, dari [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Keripik_singkong_balado_cassava_chips.JPG), dengan lisensi [CC BY-SA 3.0](https://creativecommons.org/licenses/by-sa/3.0/). Gambar diperkecil menjadi 1024×768 dan dikonversi ke PNG.
- **Font `montserrat_bold.ttf`**: Montserrat dari The Montserrat Project Authors ([Google Fonts](https://fonts.google.com/specimen/Montserrat)), dengan lisensi [SIL Open Font License 1.1](https://openfontlicense.org).

### Mengganti gambar

Taruh gambar baru di `app/src/main/res/drawable/` dengan nama `keripik.png` (huruf kecil, tanpa spasi), lalu hapus file lama. Layout tidak perlu diubah karena tetap memakai `@drawable/keripik`. Setelah itu perbarui bagian atribusi di atas.
