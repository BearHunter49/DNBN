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
    lateinit var fHome: Fragment
    lateinit var fShop: Fragment
    lateinit var fUser: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        IsRTL.ifSupported(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fragmentManager = supportFragmentManager

        // Fragment Load
        fHome = HomeFragment()
        fShop = ShopFragment()
        fUser = UserFragment()

        fragmentManager.beginTransaction().apply {
            add(R.id.Container, fHome)
            add(R.id.Container, fShop)
            add(R.id.Container, fUser).commit()
        }
        loadFrag("HOME", fragmentManager)
        imgHome.isClickable = false


        // Click Event
        imgHome.setOnClickListener {
            loadFrag("HOME", fragmentManager)

            imgHome.setImageResource(R.drawable.home_64_orange)
            imgShopping.setImageResource(R.drawable.shop_64_white)
            imgUser.setImageResource(R.drawable.user_64_white)
            imgHome.isClickable = false
            imgShopping.isClickable = true
            imgUser.isClickable = true
        }
        imgShopping.setOnClickListener {
            loadFrag("STORE", fragmentManager)

            imgHome.setImageResource(R.drawable.home_64_white)
            imgShopping.setImageResource(R.drawable.shop_64_orange)
            imgUser.setImageResource(R.drawable.user_64_white)
            imgHome.isClickable = true
            imgShopping.isClickable = false
            imgUser.isClickable = true
        }
        imgUser.setOnClickListener {
            loadFrag("개인정보", fragmentManager)

            imgHome.setImageResource(R.drawable.home_64_white)
            imgShopping.setImageResource(R.drawable.shop_64_white)
            imgUser.setImageResource(R.drawable.user_64_orange)
            imgHome.isClickable = true
            imgShopping.isClickable = true
            imgUser.isClickable = false
        }


    }

    private fun loadFrag(name: String, fm: FragmentManager) {
//        for (i in 1..fm.backStackEntryCount){
//            fm.popBackStack()
//        }
        val ft = fm.beginTransaction()
        when(name){
            "HOME" -> {
                ft.show(fHome)
                ft.hide(fShop)
                ft.hide(fUser).commit()
            }
            "STORE" -> {
                ft.hide(fHome)
                ft.show(fShop)
                ft.hide(fUser).commit()
            }
            "개인정보" -> {
                ft.hide(fHome)
                ft.hide(fShop)
                ft.show(fUser).commit()
            }
        }
        toolbar_title.text = name
    }
}
