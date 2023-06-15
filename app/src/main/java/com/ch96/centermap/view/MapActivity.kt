package com.ch96.centermap.view

import android.content.ClipData.Item
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivityMapBinding
import com.ch96.centermap.model.ItemModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage


class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMapBinding>(this, R.layout.activity_map)

        val itemModel = ItemModel(this)

        // 지도 객체 생성
        var fm = supportFragmentManager
        var mapFragment = fm.findFragmentById(R.id.map)
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
            fm.beginTransaction().add(R.id.map, mapFragment).commit()
        }

        val marker = Marker()
        marker.position = LatLng(37.5670135, 126.9783740)

//        setMarker(marker, )

        itemModel.loadData()

    }

    fun setMarker(marker:Marker, lat:Double, lng:Double, resourceID:Int){

        //원근감 표시
        marker.setIconPerspectiveEnabled(true)

        //아이콘
        marker.setIcon(OverlayImage.fromResource(resourceID))

        //마커 위치
        marker.setPosition(LatLng(lat, lng))

        //마커 표시
//        marker.setMap(naverMap)
    }
}