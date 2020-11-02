package com.swma.dnbn.view.activity

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.swma.dnbn.R
import com.swma.dnbn.view.fragment.HomeFragment
import com.swma.dnbn.view.fragment.StoreFragment
import com.swma.dnbn.view.fragment.UserFragment
import com.swma.dnbn.utils.AppRequestCode
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_toolbar.*
import java.security.MessageDigest

const val HOME_TAG: String = "HOME"
const val STORE_TAG: String = "STORE"
const val INFO_TAG: String = "개인정보"

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager
    lateinit var fHome: Fragment
    lateinit var fShop: Fragment
    lateinit var fUser: Fragment
    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // App Bar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Fragment Load
        setDefaultFragment()

        // Click Event
        imgHome.setOnClickListener {
            loadFrag(HOME_TAG, fragmentManager)
        }
        imgShopping.setOnClickListener {
            loadFrag(STORE_TAG, fragmentManager)
        }
        imgUser.setOnClickListener {
            loadFrag(INFO_TAG, fragmentManager)
        }
    }

    private fun setDefaultFragment() {
        fragmentManager = supportFragmentManager
        fHome = HomeFragment()
        fShop = StoreFragment()
        fUser = UserFragment()

        fragmentManager.beginTransaction().apply {
            add(R.id.Container, fHome, HOME_TAG)
            add(R.id.Container, fShop, HOME_TAG)
            add(R.id.Container, fUser, HOME_TAG)
            hide(fShop)
            hide(fUser)
            show(fHome).commit()
        }
        imgHome.isClickable = false
    }

    private fun loadFrag(name: String, fm: FragmentManager) {
        val ft = fm.beginTransaction()
        when (name) {
            HOME_TAG -> {
                ft.hide(fShop)
                ft.hide(fUser)
                ft.show(fHome).commit()

                imgHome.setImageResource(R.drawable.home_64_orange)
                imgShopping.setImageResource(R.drawable.shop_64_white)
                imgUser.setImageResource(R.drawable.user_64_white)
                imgHome.isClickable = false
                imgShopping.isClickable = true
                imgUser.isClickable = true
            }
            STORE_TAG -> {
                ft.hide(fHome)
                ft.hide(fUser)
                ft.show(fShop).commit()

                imgHome.setImageResource(R.drawable.home_64_white)
                imgShopping.setImageResource(R.drawable.shop_64_orange)
                imgUser.setImageResource(R.drawable.user_64_white)
                imgHome.isClickable = true
                imgShopping.isClickable = false
                imgUser.isClickable = true
            }
            INFO_TAG -> {
                ft.hide(fHome)
                ft.hide(fShop)
                ft.show(fUser).commit()

                imgHome.setImageResource(R.drawable.home_64_white)
                imgShopping.setImageResource(R.drawable.shop_64_white)
                imgUser.setImageResource(R.drawable.user_64_orange)
                imgHome.isClickable = true
                imgShopping.isClickable = true
                imgUser.isClickable = false
            }
        }
        toolbar_title.text = name
    }

    override fun onBackPressed() {
        // Title 변경
        if (fragmentManager.backStackEntryCount != 0) {
            val tag = fragmentManager.fragments.get(fragmentManager.backStackEntryCount - 1).tag
            toolbar_title.text = tag
            lytTab.visibility = View.VISIBLE
            super.onBackPressed()
        }
        // BackPress 처리
        else {
            if (doubleBackToExit) {
                super.onBackPressed()
                return
            }
            doubleBackToExit = true
            Toast.makeText(this, "한 번 더 뒤로가기 시 종료", Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                doubleBackToExit = false
            }, 2000)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
            }
            R.id.menu_gps -> {
                val intent = Intent(this, LocationActivity::class.java)
                startActivityForResult(intent, AppRequestCode.LOCATION_CODE)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("myTest", "MainActivity Result 호출")

        if (requestCode == AppRequestCode.LOCATION_CODE) {
            Toast.makeText(this, data?.getStringExtra("address"), Toast.LENGTH_SHORT).show()
            val homeFragment = fragmentManager.findFragmentByTag(HOME_TAG)
            homeFragment?.onActivityResult(requestCode, resultCode, data)
        }
    }
}
