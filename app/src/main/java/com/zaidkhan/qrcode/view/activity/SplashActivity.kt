package com.zaidkhan.qrcode.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaidkhan.qrcode.R
import com.zaidkhan.qrcode.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //For Hiding The Title Bar
        supportActionBar?.hide()

        // For Intent splash activity to Main Activity with some transition.
        val intent = Intent(this, MainActivity::class.java)
        binding.appLogo.alpha = 1f
        binding.appLogo.animate().setDuration(1500).alpha(1f).withEndAction {
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

    }
}