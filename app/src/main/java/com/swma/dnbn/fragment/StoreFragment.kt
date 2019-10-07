package com.swma.dnbn.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.swma.dnbn.R
import com.swma.dnbn.ShopActivity
import kotlinx.android.synthetic.main.fragment_store.view.*

class StoreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_store, container, false)

        rootView.apply {


            btn_test.setOnClickListener {
                requireActivity().startActivity(Intent(context, ShopActivity::class.java))
            }
        }


        return rootView
    }

}
