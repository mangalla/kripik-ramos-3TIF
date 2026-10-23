package com.example.ramkeripik.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.data.AkunManager
import com.example.ramkeripik.databinding.ActivityRegistrasiBerhasilBinding

// 02a Halaman informasi "Registrasi Berhasil" (Quiz Soal 2).
// Data dibaca ULANG dari SharedPreferences, sekaligus membuktikan registrasi benar-benar tersimpan.
class RegistrasiBerhasilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrasiBerhasilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBerhasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val akun = AkunManager(this)
        binding.tvNamaTersimpan.text = akun.ambilNama()
        binding.tvEmailTersimpan.text = akun.ambilEmail()
        binding.tvUsernameTersimpan.text = akun.ambilUsername()

        // Back stack: Login -> RegistrasiBerhasil, jadi finish() langsung kembali ke Login
        binding.btnKeLogin.setOnClickListener { finish() }
    }
}
