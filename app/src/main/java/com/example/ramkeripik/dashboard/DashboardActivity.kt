package com.example.ramkeripik.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.ActivityDashboardBinding
import com.example.ramkeripik.harga.HargaTbsActivity
import com.example.ramkeripik.web.WebDisbunActivity
import com.google.android.material.snackbar.Snackbar

// 03 Beranda: halaman utama setelah login, sengaja tanpa tombol back
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menu yang halamannya belum dibuat minggu ini
        binding.cardCatatPanen.setOnClickListener { tampilkanSegeraHadir() }
        binding.cardRiwayat.setOnClickListener { tampilkanSegeraHadir() }
        binding.cardKebun.setOnClickListener { tampilkanSegeraHadir() }
        binding.cardRekap.setOnClickListener { tampilkanSegeraHadir() }

        binding.tvLihatSemuaHarga.setOnClickListener {
            startActivity(Intent(this, HargaTbsActivity::class.java))
        }

        binding.btnWebDisbun.setOnClickListener {
            startActivity(Intent(this, WebDisbunActivity::class.java))
        }
    }

    private fun tampilkanSegeraHadir() {
        Snackbar.make(binding.root, R.string.fitur_segera_hadir, Snackbar.LENGTH_SHORT).show()
    }
}
