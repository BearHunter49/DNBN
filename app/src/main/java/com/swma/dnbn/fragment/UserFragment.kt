package com.swma.dnbn.fragment

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.swma.dnbn.R
import com.swma.dnbn.adapter.UserPagerAdapter
import com.swma.dnbn.item.ItemUser
import kotlinx.android.synthetic.main.fragment_user.view.*
import kotlinx.android.synthetic.main.row_toolbar.*


class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user, container, false)

        // Http 서버로부터 User 데이터 받기
        val user = ItemUser(1, "베어헌터", "", 59)

        rootView.apply {
            // 사진 원형으로
            imgUserProfile.background = ShapeDrawable(OvalShape())
            imgUserProfile.clipToOutline = true

            // ViewPage + TabLayout
            userViewPager.adapter = UserPagerAdapter(requireActivity().supportFragmentManager)
            tabLayout.setupWithViewPager(userViewPager)

            // User Profile (이미지 일단 빼고 함)
            textUserName.text = user.UserName
            textUserFollower.text = String.format("팔로워 %d명", user.UserFollower)
        }


        return rootView
    }


}
