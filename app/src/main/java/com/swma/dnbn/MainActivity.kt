package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.swma.dnbn.fragment.HomeFragment
import kotlinx.android.synthetic.main.row_toolbar.*

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        IsRTL.ifSupported(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fragmentManager = supportFragmentManager

        loadFrag(HomeFragment(), "Home", fragmentManager)


    }

    private fun loadFrag(fg: Fragment, name: String, fm: FragmentManager) {
        for (i in 1..fm.backStackEntryCount){
            fm.popBackStack()
        }
        val ft = fm.beginTransaction()
        ft.replace(R.id.Container, fg, name)
        ft.commit()
        toolbar_title.text = name
    }
}
