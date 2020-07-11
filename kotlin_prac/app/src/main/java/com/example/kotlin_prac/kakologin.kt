package com.example.kotlin_prac

import android.app.Application

import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        KakaoSdk.init(this, "4aa6a4a9f333441330b239400fd69260")
    }
}