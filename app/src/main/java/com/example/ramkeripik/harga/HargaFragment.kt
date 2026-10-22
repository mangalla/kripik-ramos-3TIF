package com.example.ramkeripik.harga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.FragmentHargaBinding
import com.google.android.material.snackbar.Snackbar

// Tab Harga: tabel harga TBS Disbun Riau (dulu HargaTbsActivity, data statis dari strings.xml)
class HargaFragment : Fragment() {

    private var _binding: FragmentHargaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHargaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.harga_judul)
        }

        // Data swadaya belum tersedia
        binding.cardSwadaya.setOnClickListener {
            Snackbar.make(binding.root, R.string.fitur_segera_hadir, Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
