package com.example.ramkeripik.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.BaseActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.data.SessionManager
import com.example.ramkeripik.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar

// 01 Login: dibuka SplashActivity bila belum login; sukses -> simpan sesi -> BaseActivity
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim()
            val password = binding.inputPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, R.string.login_wajib_diisi, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email != getString(R.string.login_demo_email) ||
                password != getString(R.string.login_demo_password)
            ) {
                Toast.makeText(this, R.string.login_salah, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Autentikasi berhasil -> simpan isLogin = true di SharedPreferences
            SessionManager(this).simpanLogin(email)

            startActivity(Intent(this, BaseActivity::class.java))
            // finish() agar tombol back di BaseActivity tidak kembali ke Login
            finish()
        }

        binding.tvLupaPassword.setOnClickListener {
            Snackbar.make(binding.root, R.string.fitur_segera_hadir, Snackbar.LENGTH_SHORT).show()
        }

        binding.tvKeDaftar.setOnClickListener {
            startActivity(Intent(this, DaftarAkunActivity::class.java))
        }
    }
}
