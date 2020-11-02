package com.swma.dnbn.view.fragment

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso

import com.swma.dnbn.R
import com.swma.dnbn.view.adapter.UserPagerAdapter
import com.swma.dnbn.model.item.ItemUser
import com.swma.dnbn.utils.MyApplication
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user.view.*
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
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dummy Data
        textUserName.text = MyApplication.userName
        textUserFollower.text = String.format("팔로워 %s명", getString(R.string.test_channel_follower))
        Picasso.get().load(getString(R.string.test_profile_img)).into(imgUserProfile)

        makeProfileCircle()

        // ViewPage + TabLayout
        userViewPager.adapter = UserPagerAdapter(requireActivity().supportFragmentManager)
        tabLayout.setupWithViewPager(userViewPager)
    }

    private fun makeProfileCircle() {
        imgUserProfile.background = ShapeDrawable(OvalShape())
        imgUserProfile.clipToOutline = true
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
