package com.ch96.centermap.view

import android.Manifest
import android.content.ClipData.Item
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.ch96.centermap.R
import com.ch96.centermap.databinding.ActivityMapBinding
import com.ch96.centermap.model.GV
import com.ch96.centermap.model.ItemModel
import com.ch96.centermap.viewmodel.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import ted.gun0912.clustering.naver.TedNaverClustering


class MapActivity : AppCompatActivity(),OnMapReadyCallback {

    // ViewModel 참조변수
    val vm = ViewModel(this)

    // 내위치 정보
    var myLat = 37.5670135
    var myLng = 126.9783740
    var myLocation: Location? = null
    val providerClient : FusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMapBinding>(this, R.layout.activity_map)

        //위치정보 제공 동적퍼미션
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        else if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED)
            permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        else requestMyLocation()
        Log.i("loca_oncreate_afterpermission", "$myLat,$myLng")

        // 지도 객체 생성
        var fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

        Log.i("loca_oncreate_afterfm", "$myLat,$myLng")

    }

    //퍼미션 결과 받아오기
    val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        object: ActivityResultCallback<Boolean> {
            override fun onActivityResult(result: Boolean?) {
                if (result!!) requestMyLocation() //위치요청
                else Toast.makeText(this@MapActivity, "위치정보제공을 거부합니다. \n현위치로 이동불가합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    //위치 요청
    private fun requestMyLocation() {

        //위치 검색기준 설정하는 요청 객체
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        //실시간 위치정보 갱신 요청 (퍼미션 받았는지 확인)
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) return

        providerClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
        Log.i("loca_requestmethod", "$myLat,$myLng")

    }

    //위치 검색 콜백 객체
    private val locationCallback = object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)

            myLocation = p0.lastLocation

            myLat = myLocation?.latitude?:37.5670135
            myLng = myLocation?.longitude?:126.9783740

            //위치 탐색 완료, 실시간 업데이트 종료
            providerClient.removeLocationUpdates(this)
        }
    }

    override fun onMapReady(naverMap: NaverMap) {

        val cameraPosition = CameraPosition(
            LatLng(myLat, myLng),
            12.0 // 줌 레벨
        )
        naverMap.cameraPosition = cameraPosition


        // 마커 생성
        for (p in 0 until GV.latLng.size) {
            val marker = Marker()
            val listner = vm.setMarker(this,naverMap,marker,GV.centerDatas[p])
            marker.onClickListener = listner
        }

        // [ 내위치로 ]버튼 클릭 이벤트
        val toMyLocation = findViewById<Button>(R.id.fab)
        toMyLocation.setOnClickListener{
            vm.setMyLocation(naverMap,myLat,myLng)
            Log.i("loca_clicked", "$myLat,$myLng")
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}