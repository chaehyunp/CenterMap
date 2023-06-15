package com.ch96.centermap.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ch96.centermap.model.ItemModel
import com.ch96.centermap.model.ResponseModel
import com.ch96.centermap.network.RetrofitApiService
import com.ch96.centermap.network.RetrofitHelper
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel(context: Context) {

    //view와 연결할 model 역할 클래스 참조변수
    var itemModel = ItemModel(context)

    //API 데이터 불러오기
    fun loadData(){
        val retrofit = RetrofitHelper.getRetrofitInstanse(itemModel.baseUrl)
        retrofit.create(RetrofitApiService::class.java).searchCenter(10,10,itemModel.serviceKey).enqueue(object:
            Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                var res = response.body()!!
                //Log.i("res", "$res")
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
            }

        })
    }

    // 프로그레스 변수
    var progress:LiveData<String> =  MutableLiveData("")

    // 프로그레스 바 진행값 변경 메소드
    fun setProgress(s:CharSequence, start:Int, end:Int, count:Int) {

    }

    // 마커 표시 메소드
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