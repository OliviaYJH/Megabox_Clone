package com.example.rc_aos_megabox

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.PermissionChecker
import com.example.rc_aos_megabox.databinding.ActivityNaverMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import java.util.jar.Manifest

class NaverMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityNaverMapBinding
    //val coord = LatLng(37.5670135, 126.9783740)
    val southWest = LatLng(31.43, 132.0)
    val northEast = LatLng(44.35, 132.0)
    val bounds = LatLngBounds(southWest, northEast)

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private val marker = Marker()
    private val markerList = ArrayList<Marker>()
    private val latitudeArray = arrayOf(37.5291902, 37.5406056,37.5716167, 37.5563367, 37.5682553, 37.5678947)
    private val longitudeArray = arrayOf(126.8759727, 126.8375628,126.8392641, 126.9222787, 126.8972733, 126.9450426)



    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


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

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)



    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // ?????? ?????? ??????
        naverMap.locationTrackingMode = LocationTrackingMode.Follow

        // ???????????? ?????? ?????? ???????????? ??? ?????? ???????????? ??????
        naverMap.addOnLocationChangeListener { location ->
            /*
            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()
            */


            // ??? ????????? ?????? ??????
            marker.position = LatLng(location.latitude, location.longitude)
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED
            marker.map = naverMap
        }

        //marker.position = LatLng(37.5291902, 126.8759727)
        //marker.map = naverMap

        for(j in 0..latitudeArray.size-1){
            markerList.add(Marker(LatLng(latitudeArray[j], longitudeArray[j])))
            markerList[j].map = naverMap
        }






        // ???????????? ????????? ?????? ?????????
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
        // ?????? ?????? ?????????
        naverMap.isIndoorEnabled = true

        val projection = naverMap.projection
        //val coord = projection.fromScreenLocation(PointF(100f, 100f))
        val cameraUpate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
            .finishCallback {
                // ????????? ?????? ??????
                Toast.makeText(this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show()
            }
            .cancelCallback {
                // ????????? ?????? ??????
                Toast.makeText(this, "????????? ?????? ??????", Toast.LENGTH_SHORT).show()
            }
            .pivot(PointF(0.5f, 0.5f))
        naverMap.moveCamera(cameraUpate)

        naverMap.addOnCameraChangeListener { reason, animated ->
            Log.d("NaverMap", "????????? ?????? - reason: $reason, animated: $animated")
        }

        naverMap.addOnCameraIdleListener {
            Toast.makeText(this, "????????? ????????? ??????", Toast.LENGTH_SHORT).show()
        }

        // ???????????? ?????? ??? ?????? ??? ?????? ??????
        naverMap.minZoom = 5.0
        naverMap.maxZoom = 18.0

        // ???????????? ?????? ?????? ??????
        naverMap.extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))

        // UiSettings ???????????? ????????????
        val uiSettings = naverMap.uiSettings

        // ????????? ????????????
        uiSettings.isCompassEnabled = false
        // ????????? ?????? ?????????
        uiSettings.isLocationButtonEnabled = true

        // ????????? ????????? ?????? toast??? ??????
        naverMap.setOnMapClickListener { point, coord ->
            Toast.makeText(this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT).show()
        }

        // ??? ???????????? ??? ??? ?????? toast??? ??????
        naverMap.setOnMapLongClickListener { point, coord ->
            Toast.makeText(this, "${coord.latitude}, ${coord.longitude}",
                Toast.LENGTH_SHORT).show()
        }

        naverMap.setOnSymbolClickListener { symbol ->
            if(symbol.caption == "??????????????????"){
                Toast.makeText(this, "???????????? ??????", Toast.LENGTH_SHORT).show()
                true
            }else{
                false
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)){
            if(!locationSource.isActivated){
                // ?????? ?????????
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}

// LocationManager??? ????????? GPS ????????? ??????????????? LocationSource ?????????
class GpsOnlyLocationSource(private val context: Context): LocationSource, LocationListener{
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    private var listener: LocationSource.OnLocationChangedListener?= null


    @SuppressLint("WrongConstant")
    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        if(locationManager == null){
            return
        }

        if(PermissionChecker.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            PermissionChecker.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){
            return
        }

        this.listener = listener
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER, 1000, 10f, this)
    }

    override fun deactivate() {
        if(locationManager == null){
            return
        }

        listener = null
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        listener?.onLocationChanged(location)
    }
}

