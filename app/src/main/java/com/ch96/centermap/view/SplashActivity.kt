package com.ch96.centermap.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivitySplashBinding
import com.ch96.centermap.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    // ViewModel 참조변수
    val vm = ViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        vm.updateProgressBar(progressBar)

        observeProgressBar()

    }
    fun observeProgressBar() {
        vm.isProgressBarComplete.observe(this, Observer { isComplete ->
            if (isComplete) {
                moveToMap()
                finish()
            }
        })
    }

    private fun moveToMap() {
        startActivity(Intent(this, MapActivity::class.java))
        finish()
    }



}