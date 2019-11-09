package com.swma.dnbn.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.google.zxing.integration.android.IntentIntegrator
import com.swma.dnbn.*
import com.swma.dnbn.model.InputModel
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.fragment_user_shopping.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class UserShoppingFragment : Fragment() {

    private val PERMISSIONS = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CAMERA)
    private val job = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_shopping, container, false)

        // Barcode
        val integrator = IntentIntegrator.forSupportFragment(this)
        integrator.apply {
            setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            setPrompt("Prompt!")
            setOrientationLocked(false)
            captureActivity = BarcodeActivity::class.java
        }

        rootView.apply {

            // 방송 버튼
            btn_broadcast.setOnClickListener {

                // 영상 촬영 권한
                if (!hasPermission(requireContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, 49)
                } else {
                    startActivity(Intent(requireContext(), BroadCastActivity::class.java))
                }
            }

            // 바코드 스캔 버튼
            btn_scan_barcode.setOnClickListener {
                integrator.initiateScan()
            }

            // 기프티콘함 버튼
            lytUserGifticon.setOnClickListener {
                startActivity(Intent(requireContext(), GiftIconActivity::class.java))
            }

            // 방송 생성 버튼
            btn_create_broadcast.setOnClickListener {
                Toast.makeText(requireContext(), "방송 채널 생성을 시작합니다!", Toast.LENGTH_SHORT).show()
                rootView.progressBar_create_broadcast.visibility = View.VISIBLE

                // API Gateway Instance
                val factory = ApiClientFactory()
                val client = factory.build(MedialiveapiClient::class.java)

                try {
                    CoroutineScope(Dispatchers.Default + job).launch {

                        // Input 정보
                        val body = InputModel()
                        body.action = "start"
                        body.user = MyApplication.userId.toString()
                        body.channelId = "0"

                        // 방송 정보 저장
                        MyApplication.mediaOutput = client.bylivePost(body)

                        Log.d("myTest", MyApplication.mediaOutput!!.state)
                        Log.d("myTest", MyApplication.mediaOutput!!.channelId)
                        Log.d("myTest", MyApplication.mediaOutput!!.sourceUrl)
                        Log.d("myTest", MyApplication.mediaOutput!!.destinationUrl.live)
                        Log.d("myTest", MyApplication.mediaOutput!!.destinationUrl.vod)

                        // UI
                        CoroutineScope(Dispatchers.Main + job).launch {
                            Toast.makeText(requireContext(), "약 2분 뒤에 방송 해 주세요!", Toast.LENGTH_SHORT).show()
                            rootView.progressBar_create_broadcast.visibility = View.GONE
                        }

                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }


            }

        }

        return rootView
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
        if (requestCode == 49) {
            // 권한 허용 시
            if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startActivity(Intent(requireContext(), BroadCastActivity::class.java))
            }
            // 권한 거부 시
            else {
                Toast.makeText(requireContext(), "모든 권한 허용이 필수입니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("myTest", "UserShoppingFragment 의 result")
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
            } else {
                Log.d("myTest", result.contents)
                // 결과 화면 보여주기
                val intent = Intent(requireContext(), BarcodeResultActivity::class.java)
                intent.putExtra("code", result.contents)
                startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

}
