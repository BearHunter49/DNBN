package com.swma.dnbn

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
import com.swma.dnbn.fragment.LiveShoppingFragment
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.activity_vodwatch.*

class VODWatchActivity : AppCompatActivity() {

    var currentWindow = 0
    var playbackPosition = 0L
    var playWhenReady = true

    // 임시 Url
    private lateinit var Url: String
    lateinit var player: SimpleExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_vodwatch)

        // 넘어온 VOD 영상 정보
        val vod = intent.getSerializableExtra("vod") as ItemVOD
        Url = vod.vodUrl
        val productList = vod.vodProduct


        // HTTP 통신
        // VOD 방송 정보
        VODProfile.background = ShapeDrawable(OvalShape())
        VODProfile.clipToOutline = true
        VODWatchTitle.text = "VOD 영상 테스트"
        VODWatchName.text = "베어헌터"
        VODWatchViews.text = "10,449"

        // ExoPlayer Controller Touch 리스너
        val gestureDetector = GestureDetector(this, SingleTabConfirm())
        vodExoPlayerView.setOnTouchListener(object: View.OnTouchListener{

            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if (gestureDetector.onTouchEvent(p1)){
                    Log.d("myTest", "onTouch 됨")
                    return true
                }else{
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


    }

    private inner class SingleTabConfirm: GestureDetector.SimpleOnGestureListener(){

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            Log.d("myTest", "onSingleTabUp")
            if (lytVODWatch.visibility == View.VISIBLE){
                lytVODWatch.visibility = View.GONE
                vodExoPlayerView.hideController()
            }else{
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
        vodExoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
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

        vodExoPlayerView.player = null
        player.release()
    }



}
