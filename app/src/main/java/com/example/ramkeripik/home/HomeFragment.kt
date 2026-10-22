package com.example.ramkeripik.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ramkeripik.BaseActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.FragmentHomeBinding
import com.example.ramkeripik.web.WebDisbunActivity
import com.google.android.material.snackbar.Snackbar

// Tab Beranda: isi lama DashboardActivity, sekarang berupa Fragment di dalam BaseActivity
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Di Fragment, Toolbar dipasang lewat (requireActivity() as AppCompatActivity)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.beranda_judul)
        }

        // Menu yang halamannya belum dibuat
        binding.cardCatatPanen.setOnClickListener { tampilkanSegeraHadir() }
        binding.cardRiwayat.setOnClickListener { tampilkanSegeraHadir() }
        binding.cardKebun.setOnClickListener { tampilkanSegeraHadir() }
        binding.cardRekap.setOnClickListener { tampilkanSegeraHadir() }

        // "Lihat semua" sekarang pindah ke tab Harga, bukan membuka Activity baru
        binding.tvLihatSemuaHarga.setOnClickListener {
            (requireActivity() as BaseActivity).bukaTab(R.id.harga)
        }

        // Di Fragment, Intent memakai requireContext() sebagai pengganti this
        binding.btnWebDisbun.setOnClickListener {
            startActivity(Intent(requireContext(), WebDisbunActivity::class.java))
        }
    }

    private fun tampilkanSegeraHadir() {
        Snackbar.make(binding.root, R.string.fitur_segera_hadir, Snackbar.LENGTH_SHORT).show()
    }

    // View fragment dihancurkan saat pindah tab, binding dilepas agar tidak memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
