# Flow Code: Splash → Login / Register With Gmail → SharedPreferences → BaseActivity (Bottom Navigation)

Ini **alur eksekusi kode** (fungsi / baris yang dipanggil berurutan), bukan alur layar aplikasi.
Nomor baris mengacu ke file di `app/src/main/java/com/example/ramkeripik/`.

Catatan istilah: sejak Quiz Pertemuan 8 login memakai **username** (`inputUsername`), bukan email.
User boleh masuk jika `username == password` **atau** username & password sesuai data registrasi
yang tersimpan di SharedPreferences. Halaman utama setelah login adalah `BaseActivity`
yang berisi Bottom Navigation dan 3 Fragment: Beranda, Harga, Akun (Pertemuan 7).

---

## Flow 1 — Pertama kali membuka aplikasi (belum login)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AndroidManifest.xml` baris 18–26 | Android mencari Activity ber-`LAUNCHER`, ketemu `.splash.SplashActivity` |
| **B** | `SplashActivity.onCreate()` baris 21 | Activity dibuat |
| **C** | `SplashActivity` baris 23–24 | `ActivitySplashBinding.inflate()` → `setContentView()` menampilkan logo |
| **D** | `SplashActivity` baris 26 | `handler.postDelayed(pindahHalaman, 2000L)` memasang timer 2 detik |
| **E** | (2 detik lewat) | Runnable `pindahHalaman` jalan → `tentukanHalamanTujuan()` baris 29 |
| **F** | `SplashActivity` baris 30 | `SessionManager(this)` dibuat |
| **G** | `SessionManager.isLogin()` baris 26 | `prefs.getBoolean("isLogin", false)` → kunci belum ada → **false** |
| **H** | `SplashActivity` baris 31–35 | `if (false)` → `tujuan = LoginActivity::class.java` |
| **I** | `SplashActivity` baris 36 | `startActivity(Intent(this, LoginActivity))` |
| **J** | `SplashActivity` baris 38 | `finish()` → Splash dihapus dari back stack |
| **K** | `LoginActivity.onCreate()` baris 23–26 | Halaman Login tampil |

## Flow 2 — Pengecekan username & password (tekan tombol Masuk) — Quiz Soal 2

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `LoginActivity` baris 28 | `btnMasuk.setOnClickListener { prosesLogin() }` |
| **B** | `prosesLogin()` baris 46–47 | `username = inputUsername.text.trim()`, `password = inputPassword.text` |
| **C** | baris 49 | Cek kosong → **ya**: `tampilkanError(login_wajib_diisi)` (MaterialAlertDialog) → `return` |
| **D** | baris 55 | **Kondisi 1**: `username == password` |
| **E** | baris 56 → `AkunManager.cocok()` baris 26 | **Kondisi 2**: username & password sama dengan data registrasi di `sistra_akun.xml` |
| **F** | baris 58–59 | Dua-duanya salah → MaterialAlertDialog **"Login Gagal"** → `return` |
| **G** | (lolos) | Salah satu kondisi benar → autentikasi berhasil |

## Flow 3 — Login berhasil → simpan SharedPreferences → BaseActivity (Home)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `LoginActivity` baris 64–65 | Nama & email diambil dari `AkunManager` **hanya jika** kondisi 2 (akun terdaftar) |
| **B** | baris 66 → `SessionManager.simpanLogin()` baris 16 | `isLogin=true`, `username`, `nama`, `email` disimpan di `sistra_session.xml` |
| **C** | baris 68 | `startActivity(Intent(this, BaseActivity))` |
| **D** | baris 70 | `finish()` → Login dihapus dari back stack |
| **E** | `AkunFragment` baris 40–45 | Tab Akun menampilkan nama, email, inisial dari sesi (fallback ke username bila login lewat kondisi 1) |

## Flow 4 — Membuka aplikasi lagi (sudah login)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AndroidManifest.xml` baris 18–26 | LAUNCHER → `SplashActivity` |
| **B** | `SplashActivity.onCreate()` baris 21–26 | Layout splash tampil, timer 2 detik dipasang |
| **C** | `tentukanHalamanTujuan()` baris 29 | Timer habis |
| **D** | `SessionManager.isLogin()` baris 26 | `getBoolean("isLogin", false)` → **true** |
| **E** | `SplashActivity` baris 31–32 | `tujuan = BaseActivity::class.java` |
| **F** | `SplashActivity` baris 36 | `startActivity(Intent(this, BaseActivity))` |
| **G** | `SplashActivity` baris 38 | `finish()` |
| **H** | `BaseActivity.onCreate()` | Tab Beranda tampil, **Login dilewati** |

## Flow 5 — Tombol Keluar (tab Akun)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AkunFragment` baris 52 | `btnKeluar.setOnClickListener { keluar() }` |
| **B** | `AkunFragment.keluar()` baris 68 | `SessionManager(requireContext()).logout()` |
| **C** | `SessionManager.logout()` baris 35–37 | `prefs.edit().clear().apply()` → `isLogin` hilang |
| **D** | `AkunFragment.keluar()` baris 69–71 | `Intent(LoginActivity)` + flag `NEW_TASK \| CLEAR_TASK` → `startActivity()` |
| **E** | `AkunFragment.keluar()` baris 73 | `requireActivity().finish()` → BaseActivity ditutup, Login tampil |

## Flow 6 — Profil Developer (tab Akun)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AkunFragment` baris 48 | `btnProfilDeveloper.setOnClickListener` |
| **B** | baris 49 | `startActivity(Intent(requireContext(), ProfilDeveloperActivity))` |
| **C** | `ProfilDeveloperActivity.onCreate()` | inflate `activity_profil_developer.xml` → `setSupportActionBar(toolbar)` |
| **D** | `onOptionsItemSelected()` | Ikon back (`android.R.id.home`) → `onBackPressedDispatcher.onBackPressed()` → kembali ke tab Akun |

## Flow 7 — Bottom Navigation & Fragment (Pertemuan 7)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `BaseActivity.onCreate()` baris 20 | inflate `activity_base.xml`: `FrameLayout fragment_container` + `BottomNavigationView` |
| **B** | `BaseActivity` baris 35–36 | `savedInstanceState == null` → `replaceFragment(HomeFragment())` (fragment default) |
| **C** | `BaseActivity.replaceFragment()` baris 66 | `supportFragmentManager.beginTransaction().replace(fragmentContainer, fragment).commit()` (tanpa `addToBackStack`, jadi back langsung keluar aplikasi) |
| **D** | `HomeFragment.onCreateView()` baris 22 | `FragmentHomeBinding.inflate()` |
| **E** | `HomeFragment.onViewCreated()` baris 30 | `(requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)` + pasang click listener |
| **F** | `BaseActivity` baris 39–55 | `setOnItemSelectedListener`: `R.id.beranda` / `R.id.harga` / `R.id.akun` → `replaceFragment(...)` |
| **G** | `HomeFragment` baris 46–47 | "Lihat semua" → `(requireActivity() as BaseActivity).bukaTab(R.id.harga)` → `selectedItemId` diubah → kembali ke langkah F |
| **H** | `onDestroyView()` di tiap fragment | `_binding = null` saat pindah tab agar tidak memory leak |

## Flow 8 — Register With Gmail → Registrasi → SharedPreferences (Quiz Pertemuan 8, Soal 1 & 2)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `LoginActivity` baris 40–41 | Tombol **Register With Gmail** → `EmailGmailActivity` (link "Belum punya akun? Daftar" baris 35 juga ke sini) |
| **B** | `EmailGmailActivity.prosesEmail()` baris 37 | `email = inputEmailGmail.text.trim().lowercase()` — user **hanya** mengisi email |
| **C** | baris 40 | Kosong → MaterialAlertDialog "Email tidak boleh kosong." |
| **D** | baris 46 | Bukan `@gmail.com` → MaterialAlertDialog "Email harus menggunakan domain @gmail.com." |
| **E** | baris 52 | Format salah (mis. `@gmail.com` saja) → MaterialAlertDialog |
| **F** | `lanjutKeRegistrasi()` baris 63–66 | `putExtra("extra_email_gmail", email)` → `startActivity(DaftarAkunActivity)` → `finish()` |
| **G** | `DaftarAkunActivity.onCreate()` baris 23–27 | Baca extra; jika kosong (dibuka tanpa Gmail) → kembali ke `EmailGmailActivity` |
| **H** | baris 42 | Email otomatis terisi; field dikunci di layout (`enabled=false`) |
| **I** | `validasi()` baris 65–67 | 1) Nama, Username, Password wajib diisi → `EditText.error` |
| **J** | baris 70 | 2) Username tidak boleh mengandung spasi |
| **K** | baris 75 | 3) Password minimal 6 karakter |
| **L** | baris 56 → `AkunManager.simpan()` baris 16 | Nama, email, username, password disimpan ke `sistra_akun.xml` |
| **M** | baris 58–60 | Buka halaman **Registrasi Berhasil**, form ditutup |
| **N** | `RegistrasiBerhasilActivity` baris 19–22 | Data dibaca ulang dari SharedPreferences dan ditampilkan |
| **O** | baris 25 | "Kembali ke Login" → `finish()` → Login (back stack: Login → RegistrasiBerhasil) |

Catatan: `SessionManager.logout()` hanya menghapus `sistra_session`, jadi akun di `sistra_akun` tetap ada setelah Keluar.

## Peta file

```
splash/SplashActivity.kt          ← LAUNCHER, penentu tujuan
data/SessionManager.kt            ← SharedPreferences sesi login ("sistra_session")
data/AkunManager.kt               ← SharedPreferences data registrasi ("sistra_akun")
auth/LoginActivity.kt             ← kondisi 1 (username == password) / kondisi 2 (AkunManager.cocok)
auth/EmailGmailActivity.kt        ← Soal 1: input email saja, validasi @gmail.com
auth/DaftarAkunActivity.kt        ← Soal 2: Nama, Email (terkunci), Username, Password + validasi
auth/RegistrasiBerhasilActivity.kt← halaman informasi "Registrasi Berhasil"
BaseActivity.kt                   ← halaman utama: BottomNavigationView + replaceFragment()
home/HomeFragment.kt              ← tab Beranda (isi lama Dashboard), tombol Situs Disbun
harga/HargaFragment.kt            ← tab Harga TBS (isi lama HargaTbsActivity)
akun/AkunFragment.kt              ← tab Akun: nama/email/username login, Profil Developer, Keluar
profil/ProfilDeveloperActivity.kt ← halaman profil developer
web/WebDisbunActivity.kt          ← WebView situs Disbun Riau
```
