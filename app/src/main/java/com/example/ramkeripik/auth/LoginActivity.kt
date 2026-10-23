package com.example.ramkeripik.auth

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.BaseActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.data.AkunManager
import com.example.ramkeripik.data.SessionManager
import com.example.ramkeripik.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

// 01 Login (Quiz Soal 2). User boleh masuk jika memenuhi SALAH SATU kondisi:
//   1) username == password
//   2) username & password sesuai data registrasi di SharedPreferences (AkunManager)
// Berhasil -> simpan sesi -> BaseActivity (Home). Gagal -> MaterialAlertDialog.
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.setOnClickListener { prosesLogin() }

        binding.tvLupaPassword.setOnClickListener {
            Snackbar.make(binding.root, R.string.fitur_segera_hadir, Snackbar.LENGTH_SHORT).show()
        }

        // Soal 2: email di Registrasi wajib dari Gmail, jadi link Daftar juga lewat halaman email
        binding.tvKeDaftar.setOnClickListener {
            startActivity(Intent(this, EmailGmailActivity::class.java))
        }

        // Soal 1: Register With Gmail -> halaman input email saja
        binding.btnRegisterGmail.setOnClickListener {
            startActivity(Intent(this, EmailGmailActivity::class.java))
        }
    }

    private fun prosesLogin() {
        val username = binding.inputUsername.text.toString().trim()
        val password = binding.inputPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            tampilkanError(R.string.login_wajib_diisi)
            return
        }

        val akun = AkunManager(this)
        val kondisi1 = username == password            // username == password
        val kondisi2 = akun.cocok(username, password)   // sesuai data SharedPreferences

        if (!kondisi1 && !kondisi2) {
            tampilkanError(R.string.login_salah)
            return
        }

        // Login berhasil -> simpan sesi. Nama & email hanya ada bila akunnya terdaftar (kondisi 2)
        val nama = if (kondisi2) akun.ambilNama() else null
        val email = if (kondisi2) akun.ambilEmail() else null
        SessionManager(this).simpanLogin(username, nama, email)

        startActivity(Intent(this, BaseActivity::class.java))
        // finish() agar tombol back di Home tidak kembali ke Login
        finish()
    }

    // Pesan error login ditampilkan dengan MaterialAlertDialog (syarat Soal 2)
    private fun tampilkanError(@StringRes pesan: Int) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.login_gagal_judul)
            .setMessage(pesan)
            .setPositiveButton(R.string.tombol_ok, null)
            .show()
    }
}
