package com.example.ramkeripik.akun

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ramkeripik.R
import com.example.ramkeripik.auth.LoginActivity
import com.example.ramkeripik.data.SessionManager
import com.example.ramkeripik.databinding.FragmentAkunBinding
import com.example.ramkeripik.profil.ProfilDeveloperActivity

// Tab Akun: data user yang sedang login, tombol Profil Developer, dan tombol Keluar
class AkunFragment : Fragment() {

    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.akun_judul)
        }

        // Data user diambil dari SharedPreferences yang disimpan saat login.
        // Login lewat kondisi username == password tidak punya nama/email -> pakai username.
        val session = SessionManager(requireContext())
        val username = session.ambilUsername().orEmpty()
        val nama = session.ambilNama() ?: username
        binding.tvNamaAkun.text = nama
        binding.tvEmailAkun.text = session.ambilEmail() ?: "@$username"
        binding.tvInisialAkun.text = buatInisial(nama)
        binding.tvUsernameAkun.text = username

        binding.btnProfilDeveloper.setOnClickListener {
            startActivity(Intent(requireContext(), ProfilDeveloperActivity::class.java))
        }

        binding.btnKeluar.setOnClickListener { keluar() }
    }

    // "Ramos Hutahaean" -> "RH", "ramos" -> "RA"
    private fun buatInisial(nama: String): String {
        val kata = nama.trim().split(Regex("\\s+")).filter { it.isNotEmpty() }
        val inisial = when {
            kata.isEmpty() -> "?"
            kata.size == 1 -> kata[0].take(2)
            else -> "${kata[0].first()}${kata[1].first()}"
        }
        return inisial.uppercase()
    }

    // Hapus sesi di SharedPreferences, lalu kembali ke Login dengan back stack bersih
    private fun keluar() {
        SessionManager(requireContext()).logout()
        val intent = Intent(requireContext(), LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        // Kita sedang di Fragment, jadi yang ditutup adalah BaseActivity-nya
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
