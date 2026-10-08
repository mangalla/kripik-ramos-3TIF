package com.example.ramkeripik.web

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.ramkeripik.R
import com.example.ramkeripik.databinding.ActivityWebDisbunBinding

// Halaman web Dinas Perkebunan Riau di dalam aplikasi (WebView)
class WebDisbunActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebDisbunBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebDisbunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.web_disbun_judul)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // WebViewClient agar link dibuka di dalam aplikasi, bukan di browser
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(getString(R.string.url_disbun))

        // WebView bukan nested-scrolling child, jadi appBar disembunyikan/ditampilkan manual
        binding.webView.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.appBar.setExpanded(false, true)
            } else if (scrollY < oldScrollY) {
                binding.appBar.setExpanded(true, true)
            }
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

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
