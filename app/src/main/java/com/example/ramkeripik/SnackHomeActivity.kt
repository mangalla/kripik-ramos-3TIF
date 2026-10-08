package com.example.ramkeripik

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.databinding.ActivitySnackHomeBinding

class SnackHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySnackHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySnackHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMyProject.setOnClickListener {
            val intent = Intent(this, SnackDetailActivity::class.java)
            startActivity(intent)
        }
    }
}
