package com.example.ramkeripik.auth

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.ActivityDaftarAkunBinding

// 02 Daftar Akun: tampilan saja, belum menyimpan data
class DaftarAkunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarAkunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarAkunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.daftar_judul)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.btnDaftar.setOnClickListener {
            Toast.makeText(this, R.string.daftar_berhasil, Toast.LENGTH_SHORT).show()
            // Kembali ke Login
            finish()
        }
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
}
