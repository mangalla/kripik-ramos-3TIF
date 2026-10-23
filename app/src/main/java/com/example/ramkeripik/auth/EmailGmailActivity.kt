package com.example.ramkeripik.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.ActivityEmailGmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// 01a Register With Gmail (Quiz Soal 1): user hanya menginputkan email.
// Valid (@gmail.com) -> DaftarAkunActivity dengan email terbawa. Tidak valid -> MaterialAlertDialog.
class EmailGmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailGmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailGmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.gmail_judul)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.btnLanjutkan.setOnClickListener { prosesEmail() }
    }

    private fun prosesEmail() {
        // trim() + lowercase(): "  Ramos@Gmail.com " dianggap sama dengan "ramos@gmail.com"
        val email = binding.inputEmailGmail.text.toString().trim().lowercase()

        // Validasi 1: email tidak boleh kosong
        if (email.isEmpty()) {
            tampilkanError(R.string.gmail_error_kosong)
            return
        }

        // Validasi 2: email harus memakai domain @gmail.com
        if (!email.endsWith(DOMAIN_GMAIL)) {
            tampilkanError(R.string.gmail_error_domain)
            return
        }

        // Validasi tambahan: bagian sebelum @gmail.com harus berupa format email yang benar
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tampilkanError(R.string.gmail_error_format)
            return
        }

        lanjutKeRegistrasi(email)
    }

    // Valid -> buka halaman Registrasi dan bawa email lewat Intent extra
    private fun lanjutKeRegistrasi(email: String) {
        val intent = Intent(this, DaftarAkunActivity::class.java)
        intent.putExtra(EXTRA_EMAIL, email)
        startActivity(intent)
        // finish() agar setelah Daftar Akun selesai, user kembali ke Login (bukan ke halaman ini)
        finish()
    }

    // Error ditampilkan dengan MaterialAlertDialog (syarat Soal 1)
    private fun tampilkanError(@StringRes pesan: Int) {
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.gmail_error_judul)
            .setMessage(pesan)
            .setPositiveButton(R.string.tombol_ok, null)
            .show()
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

    companion object {
        // Kunci extra yang dibaca DaftarAkunActivity
        const val EXTRA_EMAIL = "extra_email_gmail"
        private const val DOMAIN_GMAIL = "@gmail.com"
    }
}
