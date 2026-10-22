package com.example.ramkeripik.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.auth.LoginActivity
import com.example.ramkeripik.dashboard.DashboardActivity
import com.example.ramkeripik.data.SessionManager
import com.example.ramkeripik.databinding.ActivitySplashBinding

// Halaman pertama (LAUNCHER). Tampil 2 detik, lalu memilih tujuan:
// sudah login -> DashboardActivity, belum login -> LoginActivity
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())
    private val pindahHalaman = Runnable { tentukanHalamanTujuan() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler.postDelayed(pindahHalaman, DURASI_SPLASH_MS)
    }

    private fun tentukanHalamanTujuan() {
        val session = SessionManager(this)
        val tujuan = if (session.isLogin()) {
            DashboardActivity::class.java
        } else {
            LoginActivity::class.java
        }
        startActivity(Intent(this, tujuan))
        // finish() agar tombol back tidak kembali ke Splash
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacks(pindahHalaman)
        super.onDestroy()
    }

    private companion object {
        const val DURASI_SPLASH_MS = 2000L
    }
}
