package com.ch96.centermap.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ch96.centermap.R
import com.ch96.centermap.model.CenterData
import com.ch96.centermap.model.GV
import com.ch96.centermap.model.ItemModel
import com.ch96.centermap.model.NaverItem
import com.ch96.centermap.model.ResponseModel
import com.ch96.centermap.network.RetrofitApiService
import com.ch96.centermap.network.RetrofitHelper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects

class ViewModel(context: Context) {

    // view와 연결할 model 참조변수
    var itemModel = ItemModel(context)

    // API 데이터 불러오기 (page index 1 - 10 perPage 10)
    fun loadData(){
        for (i in 1 until 11) {
            val retrofit = RetrofitHelper.getRetrofitInstanse(itemModel.baseUrl)
            retrofit.create(RetrofitApiService::class.java).searchCenter(i,10,itemModel.serviceKey)
                .enqueue(object: Callback<ResponseModel> {
                    override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                        var res = response.body()!!

                        for (k in 0 until res.data.size ) {
                            // 전역변수에 데이터 저장
                            GV.centerDatas.add(res.data[k])
                            GV.latLng.add(LatLng(res.data[k].lat.toDouble(),res.data[k].lng.toDouble()))
                            Log.i("double", "${GV.latLng}")
                        }
                    }
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    }
                })
        }

    }

    // 전역변수에 있는 LatLng NaverItem 객체 리턴 for clustering(marker)
//    fun getItems():MutableList<NaverItem>{
//        val naverItem:MutableList<NaverItem> = mutableListOf()
//        for (i in 0 until GV.latLng.size) {
//            naverItem.add(i, NaverItem(GV.latLng[i]))
//        }
//        return naverItem
//    }

    // 프로그레스 변수
    var progress:LiveData<String> =  MutableLiveData("")

    // 프로그레스 바 진행값 변경 메소드
    fun setProgress(s:CharSequence, start:Int, end:Int, count:Int) {

    }

    // 내위치 오버레이
    fun setOverlay(naverMap: NaverMap, lat:Double, lng:Double){
        val locationOverlay = naverMap.locationOverlay
        locationOverlay.isVisible = true
        locationOverlay.position = LatLng(lat, lng)
    }

    // 마커 표시 메소드
    fun setMarker(context: Context, naverMap:NaverMap, marker: Marker, centerData: CenterData):Overlay.OnClickListener{

        marker.icon = MarkerIcons.GREEN
        if (centerData.centerType != "지역") marker.icon = MarkerIcons.RED
        marker.setPosition(LatLng(centerData.lat.toDouble(), centerData.lng.toDouble()))
        marker.setMap(naverMap)

        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(context) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return ("센터주소 : ${centerData.address}\n" +
                        "센터이름 : ${centerData.centerName}\n" +
                        "기관이름 : ${centerData.facilityName}\n" +
                        "전화번호 : ${centerData.phoneNumber}\n" +
                        "업데이트 : ${centerData.updatedAt}")
            }
        }

        // 마커 클릭이벤트
        val listener = Overlay.OnClickListener { overlay ->
            val marker = overlay as Marker

            if (marker.infoWindow == null) {
                // 현재 마커에 정보 창이 열려있지 않을 경우 엶
                infoWindow.open(marker)
            } else {
                // 이미 현재 마커에 정보 창이 열려있을 경우 닫음
                infoWindow.close()
            }

            // 카메라 시점 이동 (부드럽게 가감속하며 이동)
            val cameraUpdate = CameraUpdate
                .scrollTo(LatLng(centerData.lat.toDouble(), centerData.lng.toDouble()))
                .animate(CameraAnimation.Easing)
            naverMap.moveCamera(cameraUpdate)

            true
        }

        return listener
    }



}

