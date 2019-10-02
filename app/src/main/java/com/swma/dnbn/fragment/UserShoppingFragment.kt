package com.swma.dnbn.fragment

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.swma.dnbn.BroadCastActivity

import com.swma.dnbn.R
import kotlinx.android.synthetic.main.fragment_user_shopping.view.*

class UserShoppingFragment : Fragment() {

    private val PERMISSIONS = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CAMERA)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_shopping, container, false)

        rootView.apply {
            btn_broadcast.setOnClickListener {

                // 영상 촬영 권한
                if (!hasPermission(requireContext(), PERMISSIONS)) {
                    ActivityCompat.requestPermissions(requireActivity(), PERMISSIONS, 49)
                }
                else{
                    startActivity(Intent(requireContext(), BroadCastActivity::class.java))
                }
            }
        }

        return rootView
    }

    // Permit 확인
    private fun hasPermission(context: Context, permissions: Array<String>): Boolean {
        for (permit in permissions) {
            if (ActivityCompat.checkSelfPermission(context, permit) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 49) {
            // 권한 허용 시
            if (grantResults.isNotEmpty() and (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startActivity(Intent(requireContext(), BroadCastActivity::class.java))
            }
            // 권한 거부 시
            else {
                Toast.makeText(requireContext(), "모든 권한 허용이 필수입니다!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
