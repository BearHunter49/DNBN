package com.swma.dnbn.utils

import com.swma.dnbn.BuildConfig

object DebugApiKey{
    fun getKaKaoMapKey(): String{
        return BuildConfig.KAKAO_MAP_KEY
    }
}