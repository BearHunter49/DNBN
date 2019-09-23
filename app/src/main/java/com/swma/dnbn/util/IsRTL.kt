package com.swma.dnbn.util

import android.app.Activity
import android.view.View
import com.bosphere.fadingedgelayout.FadingEdgeLayout
import com.swma.dnbn.R

class IsRTL{

    companion object{
        fun ifSupported(mContext: Activity){
            mContext.window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        }

        fun changeShadowInRtl(mContext: Activity, fadingEdgeLayout: FadingEdgeLayout){
            fadingEdgeLayout.setFadeEdges(false, true, false, false)
            fadingEdgeLayout.setFadeSizes(0,
                mContext.resources.getDimensionPixelSize(R.dimen.shadow_size), 0, 0)
        }
    }

}