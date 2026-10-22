package com.example.ramkeripik.data

import android.content.Context
import android.content.SharedPreferences

// Pembungkus SharedPreferences untuk status login.
// Satu-satunya tempat yang menyentuh file preferences "sistra_session".
class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(NAMA_PREFS, Context.MODE_PRIVATE)

    // Dipanggil LoginActivity setelah email & password cocok
    fun simpanLogin(email: String) {
        prefs.edit()
            .putBoolean(KEY_IS_LOGIN, true)
            .putString(KEY_EMAIL, email)
            .apply()
    }

    // Dipanggil SplashActivity untuk menentukan halaman tujuan
    fun isLogin(): Boolean = prefs.getBoolean(KEY_IS_LOGIN, false)

    fun ambilEmail(): String? = prefs.getString(KEY_EMAIL, null)

    // Dipanggil tombol Keluar di Dashboard
    fun logout() {
        prefs.edit().clear().apply()
    }

    private companion object {
        const val NAMA_PREFS = "sistra_session"
        const val KEY_IS_LOGIN = "isLogin"
        const val KEY_EMAIL = "email"
    }
}
