package com.swma.dnbn.view.activity

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.squareup.picasso.Picasso
import com.swma.dnbn.view.adapter.ChatAdapter
import com.swma.dnbn.view.fragment.LiveShoppingFragment
import com.swma.dnbn.model.item.ItemChat
import com.swma.dnbn.model.item.ItemLive
import com.swma.dnbn.R
import com.swma.dnbn.model.item.ItemProduct
import com.swma.dnbn.utils.KeyboardHeightProvider
import com.swma.dnbn.utils.MyApplication
import kotlinx.android.synthetic.main.activity_live_watch.*
import kotlinx.android.synthetic.main.activity_live_watch.btn_follow

class LiveWatchActivity : AppCompatActivity(), KeyboardHeightProvider.KeyboardHeightObserver {

    var currentWindow = 0
    var playbackPosition = 0L
    var playWhenReady = true
    var isOnLayout = 0
    lateinit var mHandler: Handler
    private lateinit var animation: Animation
    lateinit var player: SimpleExoPlayer
    lateinit var url: String
    private var userId = 0
    lateinit var productList: ArrayList<ItemProduct>

    private lateinit var keyboardHeightProvider: KeyboardHeightProvider
    private var initialY = 0f
    private var initialYofChat = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_live_watch)

        // 넘어온 Live 정보
        val live = intent.getSerializableExtra("live") as ItemLive
        url = live.liveUrl

        // Calculate Keyboard Height
        calculateKeyboardHeight()

        //Animation
        mHandler = Handler()
        setAnimation()

        // Dummy Data
        val profileImg = getString(R.string.test_profile_img)
        val channelName = getString(R.string.test_channel)
        bindLiveData(profileImg, channelName, live.liveTitle, live.liveViewer, live.liveProduct)

        // 레이아웃 표시
        bindChattingAdapter()
        makeProfileCircle()
        displayLayout()



        // 나가기 버튼
        btn_close.setOnClickListener {
            finish()
        }

        // 팔로우 버튼
        btn_follow.setOnClickListener {
            Toast.makeText(this, "팔로우 함", Toast.LENGTH_SHORT).show()
        }

        // 채팅 입력 버튼
        btn_send.setOnClickListener {
            if (edit_chat.text.isNotEmpty()) {
                val adapter = rv_chat.adapter as ChatAdapter

                adapter.addItem(ItemChat(MyApplication.userName, edit_chat.text.toString()))
                edit_chat.text.clear()
                rv_chat.smoothScrollToPosition(0)
            }
        }

        // 상품 정보 버튼
        btn_allProduct.setOnClickListener {
            val fragment = LiveShoppingFragment(productList)
            fragment.show(supportFragmentManager, fragment.tag)
        }

        // 프로필 클릭(채널로 이동)
        LiveProfile.setOnClickListener {
            val intent = Intent(this, ChannelActivity::class.java)
            intent.putExtra("userId", userId)
            startActivity(intent)
        }
    }



    /**
     * KeyboardHeightProvider Instance
     * View가 만들어질 시간만큼 delay
     */
    private fun calculateKeyboardHeight() {
        keyboardHeightProvider = KeyboardHeightProvider(this)

        Handler().postDelayed({
            initialY = lytChatInput.y
            initialYofChat = rv_chat.y
            lytLiveWatchFull.post { keyboardHeightProvider.start() }
        }, 200)
    }

    /**
     * bind Live Data
     */
    private fun bindLiveData(
        profileImg: String,
        channelName: String,
        liveTitle: String,
        liveViewer: Int,
        liveProduct: ArrayList<ItemProduct>
    ) {
        Picasso.get().load(profileImg).into(LiveProfile)
        LiveWatchName.text = channelName
        LiveWatchTitle.text = liveTitle
        LiveWatchViewer.text = liveViewer.toString()
        productList = liveProduct
    }

    private fun makeProfileCircle() {
        LiveProfile.background = ShapeDrawable(OvalShape())
        LiveProfile.clipToOutline = true
    }

    /**
     * Chatting Layout Adapter 연결
     */
    private fun bindChattingAdapter() {
        rv_chat.apply {
            setHasFixedSize(true)
            focusable = View.NOT_FOCUSABLE
            adapter = ChatAdapter(this@LiveWatchActivity)
        }
    }

    /**
     * Animation 설정
     */
    private fun setAnimation() {
        animation = AnimationUtils.loadAnimation(this, R.anim.fadeout)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                lytLiveWatch.visibility = View.GONE
            }

            override fun onAnimationStart(p0: Animation?) {
            }
        })
    }

    /**
     * Layout 보이기
     */
    private fun displayLayout() {
        if (isOnLayout == 0) {
            isOnLayout = 1
            lytLiveWatch.visibility = View.VISIBLE

            mHandler.postDelayed({  // 4초 뒤 자동으로 레이아웃 없애기
                lytLiveWatch.startAnimation(animation)
                isOnLayout = 0
            }, 4000)
        }
    }

    /**
     * Touch 이벤트
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {

            // 레이아웃 떠 있을 시
            if (isOnLayout == 1) {
                mHandler.removeMessages(0)
                lytLiveWatch.startAnimation(animation)
                isOnLayout = 0
            }
            // 레이아웃 없을 시
            else {
                displayLayout()
            }
        }
        return super.onTouchEvent(event)
    }


    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this.applicationContext)
        exoPlayerView.player = player

        // Size, Start Point
        exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        player.seekTo(currentWindow, playbackPosition)

        val mediaSource = buildMediaSource(Uri.parse(url))

        player.prepare(mediaSource, true, true)
        player.playWhenReady = playWhenReady
    }

    private fun buildMediaSource(parse: Uri?): MediaSource {
        val userAgent = Util.getUserAgent(this, "BearHunter")
        return HlsMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent))
            .createMediaSource(parse)
    }

    private fun releasePlayer() {
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        playWhenReady = player.playWhenReady

        exoPlayerView.player = null
        player.release()
    }

    /**
     * Keyboard 높이 변경에 실행
     * @param height = Keyboard 높이
     * @param orientation = Portrait or Landscape
     */
    override fun onKeyboardHeightChanged(height: Int, orientation: Int) {
        if (height == 0) {
            lytChatInput.y = initialY
            rv_chat.y = initialYofChat
            Log.d("myTest", "KeyboardHeight Init Part")

            // 레이아웃 사이즈 변경
            lytChatInput.requestLayout()
            rv_chat.requestLayout()
        } else {
            val newPosition = initialY - height
            val newPositionOfChat = initialYofChat - height
            lytChatInput.y = newPosition
            rv_chat.y = newPositionOfChat

            // 레이아웃 사이즈 변경
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

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

}
