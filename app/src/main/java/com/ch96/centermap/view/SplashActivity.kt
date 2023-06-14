package com.ch96.centermap.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)
        
        // progressbar 2초 100% 로 수정
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MapActivity::class.java))
        }, 2000)

    }
}