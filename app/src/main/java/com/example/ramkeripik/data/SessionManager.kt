package com.example.ramkeripik.data

import android.content.Context
import android.content.SharedPreferences

// Pembungkus SharedPreferences untuk status login (sesi).
// Satu-satunya tempat yang menyentuh file preferences "sistra_session".
// Data akun hasil registrasi disimpan terpisah di AkunManager ("sistra_akun").
class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(NAMA_PREFS, Context.MODE_PRIVATE)

    // Dipanggil LoginActivity setelah login berhasil.
    // nama & email bernilai null bila masuk lewat kondisi username == password (tanpa registrasi)
    fun simpanLogin(username: String, nama: String?, email: String?) {
        prefs.edit()
            .putBoolean(KEY_IS_LOGIN, true)
            .putString(KEY_USERNAME, username)
            .putString(KEY_NAMA, nama)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    // Dipanggil SplashActivity untuk menentukan halaman tujuan
    fun isLogin(): Boolean = prefs.getBoolean(KEY_IS_LOGIN, false)

    fun ambilUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun ambilNama(): String? = prefs.getString(KEY_NAMA, null)

    fun ambilEmail(): String? = prefs.getString(KEY_EMAIL, null)

    // Nama lengkap dari registrasi; login lewat username == password tidak punya nama -> pakai username
    fun ambilNamaTampilan(): String = ambilNama() ?: ambilUsername().orEmpty()

    // Kata pertama nama, huruf awal kapital: "Ramos Hutahaean" -> "Ramos", "darwin" -> "Darwin"
    fun ambilNamaPanggilan(): String =
        ambilNamaTampilan().trim().split(Regex("\\s+")).first().replaceFirstChar { it.uppercase() }

    // "Ramos Hutahaean" -> "RH", "ramos" -> "RA" (avatar bulat di Beranda & Akun)
    fun ambilInisial(): String {
        val kata = ambilNamaTampilan().trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        val inisial = when {
            kata.isEmpty() -> "?"
            kata.size == 1 -> kata[0].take(2)
            else -> "${kata[0].first()}${kata[1].first()}"
        }
        return inisial.uppercase()
    }

    // Dipanggil tombol Keluar di tab Akun (data registrasi di AkunManager tidak ikut terhapus)
    fun logout() {
        prefs.edit().clear().apply()
    }

    private companion object {
        const val NAMA_PREFS = "sistra_session"
        const val KEY_IS_LOGIN = "isLogin"
        const val KEY_USERNAME = "username"
        const val KEY_NAMA = "nama"
        const val KEY_EMAIL = "email"
    }
}
