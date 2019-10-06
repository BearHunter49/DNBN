package com.swma.dnbn

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.swma.dnbn.adapter.ChatAdapter
import com.swma.dnbn.fragment.LiveShoppingFragment
import com.swma.dnbn.item.ItemChat
import com.swma.dnbn.util.KeyboardHeightProvider
import kotlinx.android.synthetic.main.activity_live_watch.*

class LiveWatchActivity : AppCompatActivity(), KeyboardHeightProvider.KeyboardHeightObserver {

    var currentWindow = 0
    var playbackPosition = 0L
    var playWhenReady = true
    var check = 0
    lateinit var handler: Handler
    private lateinit var animation: Animation
    // 임시 Url
    private val Url = "https://sywblelzxjjjqz.data.mediastore.ap-northeast-2.amazonaws.com/Test/main.m3u8"
    lateinit var player: SimpleExoPlayer
    lateinit var chatList: ArrayList<ItemChat>

    // Keyboard part
    private lateinit var keyboardHeightProvider: KeyboardHeightProvider
    private var initialY = 0f
    private var initialYofChat = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_live_watch)

        // Keyboard part
        keyboardHeightProvider = KeyboardHeightProvider(this)

        // Get Keyboard height
        Handler().postDelayed({
            initialY = lytChatInput.y
            initialYofChat = rv_chat.y
            lytLiveWatchFull.post { keyboardHeightProvider.start() }
        }, 200)


        // Fade-out Animation
        handler = Handler()
        animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animation.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                lytLiveWatch.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })

        // 채팅창
        chatList = ArrayList()
        rv_chat.apply {
            setHasFixedSize(true)
            focusable = View.NOT_FOCUSABLE
            adapter = ChatAdapter(this@LiveWatchActivity)
        }

        // 사라지는 레이아웃 표시
        displayLayout()

        // 소켓 통신으로 채팅 받아오기
        // 비동기로 처리

        // Live 방송 정보 Http 통신
        LiveProfile.background = ShapeDrawable(OvalShape())
        LiveProfile.clipToOutline = true
        LiveWatchTitle.text = "라이브 방송 테스트"
        LiveWatchName.text = "베어헌터"
        LiveWatchViewer.text = "51"


        // 나가기 버튼
        btn_close.setOnClickListener {
            AlertDialog.Builder(this).setTitle("경고!").setMessage("정말로 종료 하시겠습니까?")
                .setPositiveButton("확인"){ _, _ -> finish() }
                .setNegativeButton("취소", null)
                .show()
        }

        // 팔로우 버튼
        btn_follow.setOnClickListener {
            Toast.makeText(this, "팔로우 함", Toast.LENGTH_SHORT).show()
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

        // 상품 정보 버튼
        btn_allProduct.setOnClickListener {
            val fragment = LiveShoppingFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }



    }


    override fun onBackPressed() {
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    // 레이아웃 관리
    private fun displayLayout() {
        if (check == 0){
            check = 1
            lytLiveWatch.visibility = View.VISIBLE
            handler.postDelayed({
                lytLiveWatch.startAnimation(animation)
                check = 0
            }, 4000)
        }
    }

    // 터치 이벤트
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN){
            // 레이아웃 떠 있을 시
            if (check == 1){
                handler.removeMessages(0)
                lytLiveWatch.startAnimation(animation)
                check = 0
            }
            // 레이아웃 없을 시
            else{
                displayLayout()
            }
        }
        return super.onTouchEvent(event)
    }



    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this.applicationContext)
        exoPlayerView.player = player

        // Size, Start Point
        exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        player.seekTo(currentWindow, playbackPosition)

        val mediaSource = buildMediaSource(Uri.parse(Url))

        player.prepare(mediaSource, true, true)
        player.playWhenReady = playWhenReady
    }

    private fun buildMediaSource(parse: Uri?): MediaSource {
        val userAgent = Util.getUserAgent(this, "BearHunter")
        return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(parse)
    }

    private fun releasePlayer() {
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        playWhenReady = player.playWhenReady

        exoPlayerView.player = null
        player.release()
    }

    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        if (height == 0) {
            lytChatInput.y = initialY
            rv_chat.y = initialYofChat
            Log.d("myTest", "")
            lytChatInput.requestLayout()
            rv_chat.requestLayout()
        } else {
            val newPosition = initialY - height
            val newPositionofChat = initialYofChat - height
            lytChatInput.y = newPosition
            rv_chat.y = newPositionofChat

            lytChatInput.requestLayout()
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
        super.onDestroy()
        keyboardHeightProvider.close()
    }

}
