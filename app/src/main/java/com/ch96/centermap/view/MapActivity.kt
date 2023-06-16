package com.ch96.centermap.view

import android.content.ClipData.Item
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivityMapBinding
import com.ch96.centermap.model.ItemModel
import com.ch96.centermap.model.NaverItem
import com.ch96.centermap.viewmodel.ViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import ted.gun0912.clustering.naver.TedNaverClustering


class MapActivity : AppCompatActivity(),OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMapBinding>(this, R.layout.activity_map)


        // 지도 객체 생성
        var fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(naverMap: NaverMap) {
//        val marker = Marker()
//        marker.position = LatLng(37.5670135, 126.9783740)
//        marker.map
//        marker.map = naverMap
        
        // ViewModel 참조변수
        val vm = ViewModel(this)

        // 마커 라이브러리 이용
        TedNaverClustering.with<NaverItem>(this, naverMap)
            .items(vm.getItems())
            .make()

    }




}