package com.ch96.centermap.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import com.ch96.centermap.model.ItemModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage

class ViewModel(context: Context) {

    // 프로그레스 바 진행값
    var progress = 0

    //view와 연결할 model 역할 클래스 참조변수
    var itemModel = ItemModel(context)

    fun setMarker(marker: Marker, lat:Double, lng:Double, resourceID:Int){

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