package com.swma.dnbn.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.swma.dnbn.R
import java.lang.Exception
import java.security.MessageDigest

class SplashActivity : AppCompatActivity() {

    private val DURATION: Long = 1000

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashScreen()

        // Hash Key
//        try {
//            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
//            val signatures = info.signingInfo.apkContentsSigners
//            val md = MessageDigest.getInstance("SHA")
//            for (signature in signatures){
//                val md: MessageDigest
//                md = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                val key = String(Base64.encode(md.digest(), 0))
//                Log.d("Key", "$key")
//            }
//        }catch (e: Exception){
//            Log.e("Key", e.toString())
//        }
    }

    private fun splashScreen() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }, DURATION)
    }

}
