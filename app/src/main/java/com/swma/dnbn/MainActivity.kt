package com.swma.dnbn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.swma.dnbn.fragment.HomeFragment
import com.swma.dnbn.fragment.ShopFragment
import com.swma.dnbn.fragment.UserFragment
import kotlinx.android.synthetic.main.activity_main.*
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
        imgHome.isClickable = false

        // Click Event
        imgHome.setOnClickListener {
            imgHome.setImageResource(R.drawable.home_64_orange)
            imgShopping.setImageResource(R.drawable.shop_64_white)
            imgUser.setImageResource(R.drawable.user_64_white)
            loadFrag(HomeFragment(), "HOME", fragmentManager)

            imgHome.isClickable = false
            imgShopping.isClickable = true
            imgUser.isClickable = true
        }
        imgShopping.setOnClickListener {
            imgHome.setImageResource(R.drawable.home_64_white)
            imgShopping.setImageResource(R.drawable.shop_64_orange)
            imgUser.setImageResource(R.drawable.user_64_white)
            loadFrag(ShopFragment(), "STORE", fragmentManager)

            imgHome.isClickable = true
            imgShopping.isClickable = false
            imgUser.isClickable = true
        }
        imgUser.setOnClickListener {
            imgHome.setImageResource(R.drawable.home_64_white)
            imgShopping.setImageResource(R.drawable.shop_64_white)
            imgUser.setImageResource(R.drawable.user_64_orange)
            loadFrag(UserFragment(), "개인정보", fragmentManager)

            imgHome.isClickable = true
            imgShopping.isClickable = true
            imgUser.isClickable = false
        }


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
