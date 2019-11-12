package com.swma.dnbn

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.squareup.picasso.Picasso
import com.swma.dnbn.fragment.LiveShoppingFragment
import com.swma.dnbn.item.ItemVOD
import com.swma.dnbn.restApi.Retrofit2Instance
import kotlinx.android.synthetic.main.activity_vodwatch.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class VODWatchActivity : AppCompatActivity() {

    private var currentWindow = 0
    private var playbackPosition = 0L
    private var playWhenReady = true
    private lateinit var url: String
    private lateinit var player: SimpleExoPlayer
    private val job = Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_vodwatch)

        // 넘어온 VOD 영상 정보
        val vod = intent.getSerializableExtra("vod") as ItemVOD
        url = vod.vodUrl
        val productList = vod.vodProduct
        VODWatchTitle.text = vod.vodTitle
        VODWatchViews.text = "10,449"

        // HTTP 통신
        // VOD 방송 정보
        val retrofit = Retrofit2Instance.getInstance()!!

        try {
            CoroutineScope(Dispatchers.IO + job).launch {
                // 유저 정보
                retrofit.getUserFromUserId(vod.vodUserId).execute().body().let { user ->
                    CoroutineScope(Dispatchers.Main + job).launch {
                        VODWatchName.text = user!!.name
                        Picasso.get().load(user.profileImage).into(VODProfile)

                    }
                }
            }
        }catch (e: IOException){
            e.printStackTrace()
        }

        // 프로필 이미지
        VODProfile.background = ShapeDrawable(OvalShape())
        VODProfile.clipToOutline = true


        // ExoPlayer Controller Touch 리스너
        val gestureDetector = GestureDetector(this, SingleTabConfirm())
        vodExoPlayerView.setOnTouchListener(object : View.OnTouchListener {

            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if (gestureDetector.onTouchEvent(p1)) {
                    Log.d("myTest", "onTouch 됨")
                    return true
                } else {
                    Log.d("myTest", "onTouch false")
                }
                return false
            }

        })



        btn_close.setOnClickListener {
            finish()
        }

        btn_follow.setOnClickListener {
            Toast.makeText(this, "팔로우 함", Toast.LENGTH_SHORT).show()
        }

        btn_allProduct.setOnClickListener {
            val fragment = LiveShoppingFragment(productList)
            fragment.show(supportFragmentManager, fragment.tag)
        }

        // 프로필 사진 클릭(채널로 이동)
        VODProfile.setOnClickListener {
            val intent = Intent(this, ChannelActivity::class.java)
            intent.putExtra("userId", vod.vodUserId)
            startActivity(intent)
        }


    }

    private inner class SingleTabConfirm : GestureDetector.SimpleOnGestureListener() {

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Log.d("myTest", "onSingleTabUp")
            if (lytVODWatch.visibility == View.VISIBLE) {
                lytVODWatch.visibility = View.GONE
                vodExoPlayerView.hideController()
            } else {
                vodExoPlayerView.showController()
                lytVODWatch.visibility = View.VISIBLE
            }


            return true
        }
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(this.applicationContext)
        vodExoPlayerView.player = player

        // Size, Start Point
        vodExoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        player.seekTo(currentWindow, playbackPosition)

        val mediaSource = buildMediaSource(Uri.parse(url))

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

        vodExoPlayerView.player = null
        player.release()
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
