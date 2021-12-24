package com.example.rc_aos_megabox

import android.graphics.PointF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.rc_aos_megabox.databinding.ActivityNaverMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*

class NaverMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityNaverMapBinding
    //val coord = LatLng(37.5670135, 126.9783740)
    val southWest = LatLng(31.43, 132.0)
    val northEast = LatLng(44.35, 132.0)
    val bounds = LatLngBounds(southWest, northEast)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaverMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }

        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(naverMap: NaverMap) {
        // 대중교통 레이어 그룹 활성화
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
        // 실내 지도 활성화
        naverMap.isIndoorEnabled = true

        val projection = naverMap.projection
        //val coord = projection.fromScreenLocation(PointF(100f, 100f))
        val cameraUpate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
            .finishCallback {
                // 카메라 이동 완료
                Toast.makeText(this, "카메라 이동 완료", Toast.LENGTH_SHORT).show()
            }
            .cancelCallback {
                // 카메라 이동 실패
                Toast.makeText(this, "카메라 이동 취소", Toast.LENGTH_SHORT).show()
            }
            .pivot(PointF(0.5f, 0.5f))
        naverMap.moveCamera(cameraUpate)

        naverMap.addOnCameraChangeListener { reason, animated ->
            Log.d("NaverMap", "카메라 변경 - reason: $reason, animated: $animated")
        }

        naverMap.addOnCameraIdleListener {
            Toast.makeText(this, "카메라 움직임 종료", Toast.LENGTH_SHORT).show()
        }

        // 카메라의 최소 및 최대 줌 레벨 제한
        naverMap.minZoom = 5.0
        naverMap.maxZoom = 18.0

        // 카메라의 대상 영역 지정
        naverMap.extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))

        // UiSettings 인스턴스 가져오기
        val uiSettings = naverMap.uiSettings

        // 나침반 비활성화
        uiSettings.isCompassEnabled = false
        // 현위치 버튼 활성화
        uiSettings.isLocationButtonEnabled = true

        // 클릭된 지점의 좌표 toast로 표시
        naverMap.setOnMapClickListener { point, coord ->
            Toast.makeText(this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT).show()
        }

        // 롱 클릭했을 때 그 지점 toast로 표시
        naverMap.setOnMapLongClickListener { point, coord ->
            Toast.makeText(this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT).show()
        }

        naverMap.setOnSymbolClickListener { symbol ->
            if(symbol.caption == "서울특별시청"){
                Toast.makeText(this, "서울시청 클릭", Toast.LENGTH_SHORT).show()
                true
            }else{
                false
            }
        }


    }



}