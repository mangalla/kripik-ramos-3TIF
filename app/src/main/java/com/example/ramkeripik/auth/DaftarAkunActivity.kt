package com.example.ramkeripik.auth

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.data.AkunManager
import com.example.ramkeripik.databinding.ActivityDaftarAkunBinding


// 02 Registrasi (Quiz Soal 2): Nama, Email (dari Gmail, terkunci), Username, Password.
// Lolos validasi -> simpan ke SharedPreferences (AkunManager) -> halaman "Registrasi Berhasil".
class DaftarAkunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarAkunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Email wajib berasal dari Register With Gmail. Jika tidak ada, arahkan ke sana dulu.
        val emailDariGmail = intent.getStringExtra(EmailGmailActivity.EXTRA_EMAIL)
        if (emailDariGmail.isNullOrEmpty()) {
            startActivity(Intent(this, EmailGmailActivity::class.java))
            finish()
            return
        }

        binding = ActivityDaftarAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.daftar_judul)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Email otomatis terisi; field sudah dikunci di layout (enabled = false)
        binding.inputEmail.setText(emailDariGmail)

        binding.btnDaftar.setOnClickListener { prosesRegistrasi(emailDariGmail) }
    }

    private fun prosesRegistrasi(email: String) {
        val nama = binding.inputNama.text.toString().trim()
        // Username TIDAK di-trim supaya spasi di awal/akhir tetap tertangkap validasi
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()

        if (!validasi(nama, username, password)) return

        // Lolos validasi -> simpan seluruh data registrasi ke SharedPreferences
        AkunManager(this).simpan(nama, email, username, password)

        startActivity(Intent(this, RegistrasiBerhasilActivity::class.java))
        // finish(): back stack menjadi Login -> RegistrasiBerhasil, jadi form ini tidak muncul lagi
        finish()
    }

    private fun validasi(nama: String, username: String, password: String): Boolean {
        // 1. Semua field wajib diisi (Email sudah pasti terisi dari halaman Gmail)
        if (nama.isEmpty()) return tandaiError(binding.inputNama, R.string.daftar_error_nama_kosong)
        if (username.isEmpty()) return tandaiError(binding.inputUsername, R.string.daftar_error_username_kosong)
        if (password.isEmpty()) return tandaiError(binding.inputPassword, R.string.daftar_error_password_kosong)

        // 2. Username tidak boleh mengandung spasi (termasuk spasi di awal/akhir)
        if (username.any { it.isWhitespace() }) {
            return tandaiError(binding.inputUsername, R.string.daftar_error_username_spasi)
        }

        // 3. Password minimal 6 karakter
        if (password.length < PASSWORD_MIN) {
            return tandaiError(binding.inputPassword, R.string.daftar_error_password_pendek)
        }
        return true
    }

    // Tampilkan pesan di field yang salah, pindahkan fokus ke sana, lalu kembalikan false (tidak valid)
    private fun tandaiError(field: EditText, @StringRes pesan: Int): Boolean {
        field.error = getString(pesan)
        field.requestFocus()
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private companion object {
        const val PASSWORD_MIN = 6
    }
}
