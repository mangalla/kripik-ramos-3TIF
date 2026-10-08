package com.example.ramkeripik.harga

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.ActivityHargaTbsBinding
import com.google.android.material.snackbar.Snackbar

// 07 Harga TBS: tabel harga TBS Disbun Riau (data statis dari strings.xml)
class HargaTbsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHargaTbsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHargaTbsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.harga_judul)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Data swadaya belum tersedia minggu ini
        binding.cardSwadaya.setOnClickListener {
            Snackbar.make(binding.root, R.string.fitur_segera_hadir, Snackbar.LENGTH_SHORT).show()
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
