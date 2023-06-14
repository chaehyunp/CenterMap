package com.ch96.centermap.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil

import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivityMapBinding
import com.naver.maps.map.MapFragment

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMapBinding>(this, R.layout.activity_map)

    }
}