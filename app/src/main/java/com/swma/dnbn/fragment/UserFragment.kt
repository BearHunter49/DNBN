package com.swma.dnbn.fragment

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.swma.dnbn.R
import kotlinx.android.synthetic.main.fragment_user.view.*


class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user, container, false)

        rootView.apply {
            imgUserProfile.background = ShapeDrawable(OvalShape())
            imgUserProfile.clipToOutline = true
        }


        return rootView
    }


}
