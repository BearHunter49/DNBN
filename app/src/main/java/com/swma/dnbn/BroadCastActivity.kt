package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory
import com.pedro.encoder.input.video.CameraOpenException
import com.pedro.rtplibrary.rtmp.RtmpCamera2
import com.swma.dnbn.adapter.ChatAdapter
import com.swma.dnbn.item.ItemChat
import com.swma.dnbn.model.InputModel
import com.swma.dnbn.util.KeyboardHeightProvider
import kotlinx.android.synthetic.main.activity_broad_cast.*
import net.ossrs.rtmp.ConnectCheckerRtmp
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.restApiData.BroadcastData
import com.swma.dnbn.util.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class BroadCastActivity : AppCompatActivity(), ConnectCheckerRtmp, SurfaceHolder.Callback,
    KeyboardHeightProvider.KeyboardHeightObserver {

    private lateinit var rtmpCamera2: RtmpCamera2
    private var broadcastData: BroadcastData? = null

    private lateinit var animation: Animation
    private var check = 0
    lateinit var handler: Handler
    private lateinit var chatList: ArrayList<ItemChat>
    private val job = Job()
    private val retrofit = Retrofit2Instance.getInstance()!!

    // API Gateway Instance
    private val factory = ApiClientFactory()
    private val client = factory.build(MedialiveapiClient::class.java)

    // Keyboard part
    private lateinit var keyboardHeightProvider: KeyboardHeightProvider
    private lateinit var relativeView: ViewGroup
    private var initialY = 0f
    private var initialYofChat = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_broad_cast)

        // Keyboard part
        keyboardHeightProvider = KeyboardHeightProvider(this)
        relativeView = lytChatInput

        // Get Keyboard height
        Handler().postDelayed({
            initialY = relativeView.y
            initialYofChat = rv_chat.y
            lytBroadCastFull.post { keyboardHeightProvider.start() }
        }, 200)


        // Fade-out Animation
        handler = Handler()
        animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animation.setAnimationListener(object : Animation.AnimationListener {
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

            if (btn_broadcastStart.text == "초기화"){
                btn_broadcastStart.text = "촬영시작"
                rtmpCamera2.stopStream()
                return@setOnClickListener
            }

            if (!rtmpCamera2.isStreaming) {
                if (rtmpCamera2.prepareAudio() and rtmpCamera2.prepareVideo()) {

                    // 프로그래스 바
                    progressBar_broadcast.visibility = View.VISIBLE

                    // Http 통신
                    // 서버에 방송 정보 저장
                    try {
                        CoroutineScope(Dispatchers.Default + job).launch {

                            // 서버에 정보 전달
                            retrofit.getChannelFromUserId(MyApplication.userId).execute().body().let { channel ->
                                retrofit.getBroadcastFromChannelId(channel!!.id).execute().body()
                                    ?.forEach { broadcast ->

                                        // broadcastId 찾기
                                        // 0: 방송 전 1: 방송 중 2: 방송 끝
                                        if (broadcast.broadcastState == 1) {
                                            broadcastData = broadcast
                                            return@forEach
                                        }
                                    }


                                // 가능한 방송을 찾으면
                                if (broadcastData != null && MyApplication.mediaOutput != null) {

                                    // Live URL
                                    var liveURL = MyApplication.mediaOutput!!.destinationUrl.live
                                    val liveBackURL = liveURL.split("//")[1]
                                    liveURL = String.format("https://%s.m3u8", liveBackURL)
                                    Log.d("myTest", liveURL)

                                    // VOD URL
                                    var vodURL = MyApplication.mediaOutput!!.destinationUrl.vod
                                    val vodBackURL = vodURL!!.split("bylivetest")[1]
                                    vodURL = String.format(
                                        "https://bylivetest.s3.ap-northeast-2.amazonaws.com%s.m3u8",
                                        vodBackURL
                                    )
                                    Log.d("myTest", vodURL)

                                    // Broadcast 테이블 데이터 수정
                                    // retrofit.changeBroadcastData(broadcastData.id, liveURL, MyApplication.mediaOutput.channel_id).execute()

                                    // retrofit.addVideoData(broadcastData.id, broadcastData.title, user.id, vodURL, broadcastData.categoryId,
                                    //              "description", broadcastData.thumbnailUrl).execute()


                                }
                            }

                            // UI
                            CoroutineScope(Dispatchers.Main + job).launch {

                                // 방송 가능한게 없다면
                                if (broadcastData == null && MyApplication.mediaOutput == null) {
                                    Toast.makeText(this@BroadCastActivity, "진행 가능한 방송이 없습니다!", Toast.LENGTH_SHORT)
                                        .show()
                                } else {
                                    rtmpCamera2.startStream(MyApplication.mediaOutput!!.sourceUrl)
                                }
                                progressBar_broadcast.visibility = View.GONE
                            }


                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }


                } else {
                    Toast.makeText(this, "다시 시도 해 주세요!", Toast.LENGTH_SHORT).show()
                }
            } else {
                btn_broadcastStart.text = "촬영시작"
                stopStreaming()
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
                    .setPositiveButton("확인") { _, _ -> finish() }
                    .setNegativeButton("취소", null)
                    .show()
            }
        }

        // 채팅 입력 버튼
        btn_send.setOnClickListener {
            if (edit_chat.text.isNotEmpty()) {
                val adapter = rv_chat.adapter as ChatAdapter

                adapter.addItem(ItemChat(1, edit_chat.text.toString()))
                edit_chat.text.clear()
                rv_chat.smoothScrollToPosition(0)
            }
        }


    }

    private fun stopStreaming(){

        // Http 통신 방송 끄기
        try {
            CoroutineScope(Dispatchers.Default + job).launch {

                if (rtmpCamera2.isStreaming && broadcastData != null && MyApplication.mediaOutput != null) {

                    // 방송 끄기
                    val body = InputModel()
                    body.action = "stop"
                    body.user = MyApplication.userId.toString()
                    body.channelId = MyApplication.mediaOutput!!.channelId

                    val output = client.byliveStopPost(body)

                    Log.d("myTest", output.state)
                    Log.d("myTest", output.channelId)

                    // Broadcast 서버로 정보 수정하기
                    // retrofit.changeBroadcastState(broadcastData.id).execute()


                    // UI
                    CoroutineScope(Dispatchers.Main + job).launch {
                        // 방송 끄기
                        rtmpCamera2.stopStream()
                    }

                }

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    // 터치 이벤트
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            // 레이아웃 떠 있을 시
            if (check == 1) {
                handler.removeMessages(0)
                lytBroadCast.startAnimation(animation)
                check = 0
            }
            // 레이아웃 없을 시
            else {
                displayLayout()
            }
        }
        return super.onTouchEvent(event)
    }

    // 레이아웃 관리
    private fun displayLayout() {
        if (check == 0) {
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
            btn_broadcastStart.text = "방송끄기"
        }
    }

    override fun onConnectionFailedRtmp(reason: String?) {
        runOnUiThread {
            Toast.makeText(this, "아직 준비가 안됐습니다!", Toast.LENGTH_SHORT).show()
            btn_broadcastStart.text = "초기화"
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

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        if (height == 0) {
            relativeView.y = initialY
            rv_chat.y = initialYofChat

            relativeView.requestLayout()
            rv_chat.requestLayout()
        } else {
            val newPosition = initialY - height
            val newPositionofChat = initialYofChat - height
            relativeView.y = newPosition
            rv_chat.y = newPositionofChat

            relativeView.requestLayout()
            rv_chat.requestLayout()
        }
    }

    override fun onPause() {
        super.onPause()
        keyboardHeightProvider.setKeyboardHeightObserver(null)
    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider.setKeyboardHeightObserver(this)
    }

    override fun onDestroy() {
        stopStreaming()
        keyboardHeightProvider.close()
        job.cancel()
        super.onDestroy()
    }


}
