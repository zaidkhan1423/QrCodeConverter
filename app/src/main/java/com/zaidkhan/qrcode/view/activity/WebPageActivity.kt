package com.zaidkhan.qrcode.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaidkhan.qrcode.databinding.ActivityWebPageBinding

class WebPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebPageBinding
    private var url: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent != null) {
            val bundle: Bundle? = intent.extras
            url = bundle?.getString("URL")
//            webView.settings.javaScriptEnabled = true
           binding.webView.loadUrl(url.toString())
        }
    }
}