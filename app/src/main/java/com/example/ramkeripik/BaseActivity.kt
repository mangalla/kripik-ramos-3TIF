package com.example.ramkeripik

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ramkeripik.akun.AkunFragment
import com.example.ramkeripik.databinding.ActivityBaseBinding
import com.example.ramkeripik.harga.HargaFragment
import com.example.ramkeripik.home.HomeFragment

// Activity utama setelah login (Pertemuan 7): satu Activity, isi halaman diganti lewat Fragment.
// Bottom Navigation selalu tampil: Beranda, Harga, Akun.
class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Beri jarak untuk status bar, tapi bagian bawah = 0
        // supaya BottomNavigation tidak punya celah kosong di bawahnya
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        /** HomeFragment sebagai fragment default.
         *  Dicek savedInstanceState agar saat layar diputar fragment yang sedang dibuka tidak di-reset */
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.harga -> {
                    replaceFragment(HargaFragment())
                    true
                }
                R.id.akun -> {
                    replaceFragment(AkunFragment())
                    true
                }
                else -> false // return false jika item tidak dikenali
            }
        }

        // Menekan tab yang sedang aktif tidak memuat ulang fragment
        binding.bottomNavView.setOnItemReselectedListener { }
    }

    // Dipanggil fragment untuk pindah tab, mis. "Lihat semua" di Beranda -> tab Harga
    fun bukaTab(@IdRes itemId: Int) {
        binding.bottomNavView.selectedItemId = itemId
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dinonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}
