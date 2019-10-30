package com.swma.dnbn.fragment

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import com.swma.dnbn.R
import com.swma.dnbn.adapter.UserPagerAdapter
import com.swma.dnbn.item.ItemUser
import com.swma.dnbn.restApi.Retrofit2Instance
import com.swma.dnbn.util.MyApplication
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.android.synthetic.main.row_toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException


class UserFragment : Fragment() {

    private val job = Job()
    lateinit var userData: ItemUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user, container, false)

        // Http 서버로부터 User 데이터 받기
        val retrofit = Retrofit2Instance.getInstance()!!

        try {
            CoroutineScope(Dispatchers.Default + job).launch {
                retrofit.getUserFromUserId(MyApplication.userId).execute().body().let { user ->

                    // UI
                    CoroutineScope(Dispatchers.Main + job).launch {
                        rootView.apply {
                            textUserName.text = user!!.name
                            textUserFollower.text = String.format("팔로워 %d명", 59)
                            Picasso.get().load(user.profileImage).into(imgUserProfile)
                        }
                    }
                }

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        rootView.apply {
            // 사진 원형으로
            imgUserProfile.background = ShapeDrawable(OvalShape())
            imgUserProfile.clipToOutline = true

            // ViewPage + TabLayout
            userViewPager.adapter = UserPagerAdapter(requireActivity().supportFragmentManager)
            tabLayout.setupWithViewPager(userViewPager)
        }


        return rootView
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
