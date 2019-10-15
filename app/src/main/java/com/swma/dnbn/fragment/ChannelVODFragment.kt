package com.swma.dnbn.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.swma.dnbn.R
import com.swma.dnbn.adapter.ChannelVODAdapter
import com.swma.dnbn.item.ItemVOD
import kotlinx.android.synthetic.main.fragment_channel_vod.view.*

class ChannelVODFragment(private val vodList: ArrayList<ItemVOD>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_channel_vod, container, false)

        rootView.apply {

            rv_channelVOD.apply {
                setHasFixedSize(true)
                focusable = View.NOT_FOCUSABLE
                layoutManager = LinearLayoutManager(requireContext())
                adapter = ChannelVODAdapter(requireActivity(), vodList)
            }


        }


        return rootView
    }

}
