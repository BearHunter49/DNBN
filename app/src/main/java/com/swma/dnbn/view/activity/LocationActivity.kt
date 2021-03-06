package com.swma.dnbn.view.activity

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.swma.dnbn.model.restApi.Retrofit2Instance
import com.swma.dnbn.R
import com.swma.dnbn.utils.DebugApiKey
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
                MapReverseGeoCoder(DebugApiKey.getKaKaoMapKey(), mapPoint, this, this)
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
        Toast.makeText(this, "주소 변환 실패!", Toast.LENGTH_SHORT).show()
    }

    // 주소 변환 성공
    override fun onReverseGeoCoderFoundAddress(p0: MapReverseGeoCoder?, p1: String?) {

        // 주소 없으면
        if (p1.isNullOrEmpty()){
            Toast.makeText(this, "현 위치 구 주소가 없습니다...", Toast.LENGTH_SHORT).show()
            return
        }

        // 0 = 시, 1 = 구, 2 = 동, 3 = 지번
        val addressList = p1.split(" ")
        Log.d("myTest", addressList.toString())

        // Http 통신으로 유저 위치 정보 수정
//        try {
//            CoroutineScope(Dispatchers.Default).launch {
//                val response = retrofit.changeUserLocation(addressList[0], addressList[1], addressList[2], addressList[3], MyApplication.userId).execute()
//
//                if (response.isSuccessful){
//                    val intent = Intent()
//                    intent.putExtra("address", p1)
//                    setResult(Activity.RESULT_OK, intent)
//                    finish()
//
//                }
//                else{
//                    // UI
//                    CoroutineScope(Dispatchers.Main).launch {
//                        Toast.makeText(this@LocationActivity, "다시 시도 해 주세요!", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//
//
//            }
//        }catch (e: IOException){
//            e.printStackTrace()
//        }

        // 그냥 리턴
        intent.putExtra("address", p1)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }


}
