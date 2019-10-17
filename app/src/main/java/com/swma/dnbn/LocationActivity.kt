package com.swma.dnbn

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_location.*
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapReverseGeoCoder
import net.daum.mf.map.api.MapView

class LocationActivity : AppCompatActivity(), MapView.CurrentLocationEventListener,
    MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private lateinit var mapPoint: MapPoint

    private val PERMISSIONS = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // 툴바
        setSupportActionBar(toolbar_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_location_title.text = "내 위치"

        mapView.apply {
            setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.504480, 127.049016), false)
            setZoomLevelFloat(0.5f, false)
        }

        // 위치 권한 확인
        if (!hasPermission(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 200)
        } else {
            // Map 설정
            mapView.apply {
                mapType = MapView.MapType.Standard
                setCurrentLocationEventListener(this@LocationActivity)
                currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                setZoomLevelFloat(0.5f, false)
            }
        }


        // 위치 지정 버튼
        btn_location.setOnClickListener {
            val mReverseGeocoder =
                MapReverseGeoCoder(getString(R.string.kakao_AppKey), mapPoint, this, this)
            mReverseGeocoder.startFindingAddress()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // Permit 확인
    private fun hasPermission(context: Context, permissions: Array<String>): Boolean {
        for (permit in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permit) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 200) {
            // 권한 허용 시
            if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // Map 설정
                mapView.apply {
                    mapType = MapView.MapType.Standard
                    setCurrentLocationEventListener(this@LocationActivity)
                    currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
                    setZoomLevelFloat(0.5f, false)
                }
            }
            // 권한 거부 시
            else {
                Toast.makeText(this, "위치 권한 허용이 필수입니다!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {

    }

    // 현 위치 업데이트
    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
        mapPoint = p1!!
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
    }


    override fun onReverseGeoCoderFailedToFindAddress(p0: MapReverseGeoCoder?) {
    }

    // 주소 변환 성공
    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {
        Toast.makeText(this, p1, Toast.LENGTH_SHORT).show()
    }


}
