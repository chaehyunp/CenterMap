package com.ch96.centermap.model

import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

class GV{
    // API로 받아올 위치 정보 배열 - 전역변수
    companion object{
        var latLng:MutableList<LatLng> = mutableListOf()
        var centerDatas:MutableList<CenterData> = mutableListOf()
    }
}

data class ResponseModel(var data:MutableList<CenterData>)

data class CenterData( var id:Int,
                       var centerName:String,
                       var sido:String,
                       var sigungu:String,
                       var facilityName:String,
                       var zipCode:String,
                       var address:String,
                       var lat:String,
                       var lng:String,
                       var createdAt:String,
                       var updatedAt:String,
                       var centerType:String,
                       var org:String,
                       var phoneNumber:String
)


data class NaverItem(var position: LatLng) : TedClusterItem {
    override fun getTedLatLng(): TedLatLng {
        return TedLatLng(position.latitude, position.longitude)
    }

}