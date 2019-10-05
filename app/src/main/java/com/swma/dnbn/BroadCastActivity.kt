package com.swma.dnbn

import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedro.encoder.input.video.CameraOpenException
import com.pedro.rtplibrary.rtmp.RtmpCamera2
import com.swma.dnbn.adapter.ChatAdapter
import com.swma.dnbn.item.ItemChat
import kotlinx.android.synthetic.main.activity_broad_cast.*
import net.ossrs.rtmp.ConnectCheckerRtmp

class BroadCastActivity : AppCompatActivity(), ConnectCheckerRtmp, SurfaceHolder.Callback {

    private lateinit var rtmpCamera2: RtmpCamera2
    private val streamUrl = "rtmp://13.125.40.80:1935/dnbn/Test"
    private lateinit var animation: Animation
    private var check = 0
    lateinit var handler: Handler
    private lateinit var chatList: ArrayList<ItemChat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_broad_cast)

        // Fade-out Animation
        handler = Handler()
        animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                lytBroadCast.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        // RTMP Instance 연결
        rtmpCamera2 = RtmpCamera2(openglView, this)
        openglView.holder.addCallback(this)

        displayLayout()

        // 채팅창
        chatList = ArrayList()
        rv_chat.apply {
            setHasFixedSize(true)
            focusable = View.NOT_FOCUSABLE
//            layoutManager = LinearLayoutManager(this@BroadCastActivity)
            adapter = ChatAdapter(this@BroadCastActivity)
        }

        // 소켓 통신으로 채팅 정보 받아오기
        //




        // 방송 버튼
        btn_broadcastStart.setOnClickListener {
            if (!rtmpCamera2.isStreaming) {
                if (rtmpCamera2.prepareAudio() and rtmpCamera2.prepareVideo()) {
                    btn_broadcastStart.text = "방송끄기"
                    rtmpCamera2.startStream(streamUrl)
                } else {
                    Toast.makeText(this, "다시 시도 해 주세요!", Toast.LENGTH_SHORT).show()
                }
            } else {
                btn_broadcastStart.text = "촬영시작"
                rtmpCamera2.stopStream()
            }
        }

        // 화면 전환 버튼
        btn_switch.setOnClickListener {
            try {
                rtmpCamera2.switchCamera()
            } catch (e: CameraOpenException) {
                e.printStackTrace()
            }
        }

        // 나가기 버튼
        btn_close.setOnClickListener {
            if (rtmpCamera2.isStreaming) {
                Toast.makeText(this, "방송을 먼저 종료 해 주세요!", Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this).setTitle("경고!").setMessage("정말로 종료 하시겠습니까?")
                    .setPositiveButton("확인"){ _, _ -> finish() }
                    .setNegativeButton("취소", null)
                    .show()
            }
        }

        // 채팅 입력 버튼
        btn_send.setOnClickListener {
            if (edit_chat.text.isNotEmpty()){
                val adapter = rv_chat.adapter as ChatAdapter

                adapter.addItem(ItemChat("베어헌터", edit_chat.text.toString()))
                edit_chat.text.clear()
                rv_chat.smoothScrollToPosition(0)
            }
        }



    }

    // 터치 이벤트
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            // 레이아웃 떠 있을 시
            if (check == 1){
                handler.removeMessages(0)
                lytBroadCast.startAnimation(animation)
                check = 0
            }
            // 레이아웃 없을 시
            else{
                displayLayout()
            }
        }
        return super.onTouchEvent(event)
    }

    // 레이아웃 관리
    private fun displayLayout() {
        if (check == 0){
            check = 1
            lytBroadCast.visibility = View.VISIBLE
            handler.postDelayed({
                lytBroadCast.startAnimation(animation)
                check = 0
            }, 4000)
        }
    }


    override fun onBackPressed() {
    }

    // ----- 방송 인터페이스 -----
    override fun onAuthSuccessRtmp() {
    }

    override fun onNewBitrateRtmp(bitrate: Long) {
    }

    // 채널 연결 성공 시
    override fun onConnectionSuccessRtmp() {
        runOnUiThread {
            textCountDown.visibility = View.VISIBLE
            val countDownTimer = object : CountDownTimer(3000, 1000) {

                override fun onTick(p0: Long) {
                    textCountDown.text = String.format("%d", (p0 / 1000L) + 1)
                }

                override fun onFinish() {
                    textCountDown.visibility = View.GONE
                    Toast.makeText(this@BroadCastActivity, "방송을 시작합니다!", Toast.LENGTH_SHORT).show()
                }
            }
            countDownTimer.start()
        }
    }

    override fun onConnectionFailedRtmp(reason: String?) {
        runOnUiThread {
            Toast.makeText(this, "다시 시도 해 주세요!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAuthErrorRtmp() {
    }

    override fun onDisconnectRtmp() {
        runOnUiThread {
            Toast.makeText(this, "방송 종료", Toast.LENGTH_SHORT).show()
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        rtmpCamera2.startPreview()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        if (rtmpCamera2.isStreaming) {
            rtmpCamera2.stopStream()
        }
        rtmpCamera2.stopPreview()
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
    }


}
