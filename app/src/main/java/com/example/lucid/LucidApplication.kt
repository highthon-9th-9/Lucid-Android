package com.example.lucid

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class LucidApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "37aba8443bea8062751d30a9946ceaab")
    }
}