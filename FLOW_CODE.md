# Flow Code: Splash → Login → SharedPreferences → BaseActivity (Bottom Navigation)

Ini **alur eksekusi kode** (fungsi / baris yang dipanggil berurutan), bukan alur layar aplikasi.
Nomor baris mengacu ke file di `app/src/main/java/com/example/ramkeripik/`.

Catatan istilah: aplikasi ini memakai **email sebagai username**. "Pengecekan username dan password"
= pengecekan `inputEmail` dan `inputPassword`. Halaman utama setelah login adalah `BaseActivity`
yang berisi Bottom Navigation dan 3 Fragment: Beranda, Harga, Akun (Pertemuan 7).
`DashboardActivity` dan `HargaTbsActivity` sudah dihapus dan isinya pindah ke `HomeFragment` dan `HargaFragment`.

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
| **G** | `SessionManager.isLogin()` baris 22 | `prefs.getBoolean("isLogin", false)` → kunci belum ada → **false** |
| **H** | `SplashActivity` baris 31–35 | `if (false)` → `tujuan = LoginActivity::class.java` |
| **I** | `SplashActivity` baris 36 | `startActivity(Intent(this, LoginActivity))` |
| **J** | `SplashActivity` baris 38 | `finish()` → Splash dihapus dari back stack |
| **K** | `LoginActivity.onCreate()` baris 18–21 | Halaman Login tampil |

## Flow 2 — Pengecekan username & password (tekan tombol Masuk)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `LoginActivity` baris 23 | `btnMasuk.setOnClickListener` dipicu |
| **B** | baris 24 | `email = inputEmail.text.toString().trim()` |
| **C** | baris 25 | `password = inputPassword.text.toString()` |
| **D** | baris 27 | Cek kosong: `email.isEmpty() \|\| password.isEmpty()` |
| **D-ya** | baris 28–29 | Toast "Email dan password wajib diisi" → `return@setOnClickListener` (berhenti) |
| **E** | baris 32–34 | Cek cocok: `email != login_demo_email \|\| password != login_demo_password` |
| **E-ya** | baris 35–36 | Toast "Email atau password salah" → `return@setOnClickListener` (berhenti) |
| **F** | (lolos D dan E) | Autentikasi berhasil |

Data pembanding ada di `res/values/strings.xml`:
`login_demo_email = darwin@sistra.id`, `login_demo_password = sawit123` (akun demo statis, belum ada database).

## Flow 3 — Login berhasil → simpan SharedPreferences → BaseActivity

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `LoginActivity` baris 40 | `SessionManager(this).simpanLogin(email)` |
| **B** | `SessionManager.simpanLogin()` baris 14–18 | `prefs.edit().putBoolean("isLogin", true).putString("email", email).apply()` |
| **C** | (hasil) | File `sistra_session.xml` berisi `isLogin=true` |
| **D** | `LoginActivity` baris 42 | `startActivity(Intent(this, BaseActivity))` |
| **E** | `LoginActivity` baris 44 | `finish()` → Login dihapus dari back stack |
| **F** | `BaseActivity.onCreate()` | Tab Beranda tampil (lihat Flow 7) |

## Flow 4 — Membuka aplikasi lagi (sudah login)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AndroidManifest.xml` baris 18–26 | LAUNCHER → `SplashActivity` |
| **B** | `SplashActivity.onCreate()` baris 21–26 | Layout splash tampil, timer 2 detik dipasang |
| **C** | `tentukanHalamanTujuan()` baris 29 | Timer habis |
| **D** | `SessionManager.isLogin()` baris 22 | `getBoolean("isLogin", false)` → **true** |
| **E** | `SplashActivity` baris 31–32 | `tujuan = BaseActivity::class.java` |
| **F** | `SplashActivity` baris 36 | `startActivity(Intent(this, BaseActivity))` |
| **G** | `SplashActivity` baris 38 | `finish()` |
| **H** | `BaseActivity.onCreate()` | Tab Beranda tampil, **Login dilewati** |

## Flow 5 — Tombol Keluar (tab Akun)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AkunFragment` baris 45 | `btnKeluar.setOnClickListener { keluar() }` |
| **B** | `AkunFragment.keluar()` baris 50 | `SessionManager(requireContext()).logout()` |
| **C** | `SessionManager.logout()` baris 27–29 | `prefs.edit().clear().apply()` → `isLogin` hilang |
| **D** | `AkunFragment.keluar()` baris 51–53 | `Intent(LoginActivity)` + flag `NEW_TASK \| CLEAR_TASK` → `startActivity()` |
| **E** | `AkunFragment.keluar()` baris 55 | `requireActivity().finish()` → BaseActivity ditutup, Login tampil |

## Flow 6 — Profil Developer (tab Akun)

| Langkah | Lokasi | Yang terjadi |
|---|---|---|
| **A** | `AkunFragment` baris 41 | `btnProfilDeveloper.setOnClickListener` |
| **B** | baris 42 | `startActivity(Intent(requireContext(), ProfilDeveloperActivity))` |
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

## Peta file

```
splash/SplashActivity.kt          ← LAUNCHER, penentu tujuan
data/SessionManager.kt            ← satu-satunya akses SharedPreferences ("sistra_session")
auth/LoginActivity.kt             ← cek email + password, panggil simpanLogin()
BaseActivity.kt                   ← halaman utama: BottomNavigationView + replaceFragment()
home/HomeFragment.kt              ← tab Beranda (isi lama Dashboard), tombol Situs Disbun
harga/HargaFragment.kt            ← tab Harga TBS (isi lama HargaTbsActivity)
akun/AkunFragment.kt              ← tab Akun: email login, Profil Developer, Keluar
profil/ProfilDeveloperActivity.kt ← halaman profil developer
web/WebDisbunActivity.kt          ← WebView situs Disbun Riau
```
