package com.swma.dnbn

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.swma.dnbn.fragment.HomeFragment
import com.swma.dnbn.fragment.StoreFragment
import com.swma.dnbn.fragment.UserFragment
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.security.MessageDigest
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var fragmentManager: FragmentManager
    lateinit var fHome: Fragment
    lateinit var fShop: Fragment
    lateinit var fUser: Fragment
    private var doubleBackToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        IsRTL.ifSupported(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        fragmentManager = supportFragmentManager

        // Fragment Load
        fHome = HomeFragment()
        fShop = StoreFragment()
        fUser = UserFragment()

        fragmentManager.beginTransaction().apply {
            add(R.id.Container, fHome, "HOME")
            add(R.id.Container, fShop, "STORE")
            add(R.id.Container, fUser, "개인정보").commit()
        }
        loadFrag("HOME", fragmentManager)
        imgHome.isClickable = false

        // Http 통신
        // 유저 정보 받아오기
//        try {
//            CoroutineScope(Dispatchers.Default).launch {
//                val retrofit = Retrofit2Instance.getInstance()!!
//                retrofit.getUserFromUserId(MyApplication.userId).execute().body().let { user ->
//                    MyApplication.userName = user!!.name
//                }
//            }
//        }catch (e: IOException){
//            e.printStackTrace()
//        }


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
                ft.hide(fShop)
                ft.hide(fUser)
                ft.show(fHome).commit()
            }
            "STORE" -> {
                ft.hide(fHome)
                ft.hide(fUser)
                ft.show(fShop).commit()
            }
            "개인정보" -> {
                ft.hide(fHome)
                ft.hide(fShop)
                ft.show(fUser).commit()
            }
        }
        toolbar_title.text = name
    }

    override fun onBackPressed() {
        // Title 바꿔주기
        if (fragmentManager.backStackEntryCount != 0){
            val tag = fragmentManager.fragments.get(fragmentManager.backStackEntryCount - 1).tag
            toolbar_title.text = tag
            lytTab.visibility = View.VISIBLE
            super.onBackPressed()
        }
        // 앱 종료하기
        else{
            if(doubleBackToExit){
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
        when(item.itemId){
            R.id.menu_search -> {}
            R.id.menu_gps -> {
                val intent = Intent(this, LocationActivity::class.java)
                startActivityForResult(intent, 25)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("myTest", "MainActivity Result 호출")

        if (requestCode == 25){
            Toast.makeText(this, data?.getStringExtra("address"), Toast.LENGTH_SHORT).show()
            val homeFragment = supportFragmentManager.findFragmentByTag("HOME")
            homeFragment?.onActivityResult(requestCode, resultCode, data)
        }


    }


}
