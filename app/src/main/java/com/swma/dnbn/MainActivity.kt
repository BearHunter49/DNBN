package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.swma.dnbn.util.IsRTL
import kotlinx.android.synthetic.main.row_toolbar.*

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        IsRTL.ifSupported(this)
        setSupportActionBar(toolbar)
        fragmentManager = supportFragmentManager



    }
}
