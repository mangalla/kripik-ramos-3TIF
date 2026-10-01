package com.example.ramkeripik

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.databinding.ActivitySnackDetailBinding

class SnackDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySnackDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnackDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
