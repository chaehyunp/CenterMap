package com.ch96.centermap.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivitySplashBinding
import com.ch96.centermap.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        // ViewModel 참조변수
        val vm = ViewModel(this)

        // API 데이터 불러오기
        vm.loadData()
        
        // progressbar 2초 100% 로 수정
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MapActivity::class.java))
        }, 2000)


    }

}