package com.example.myhairdiary_c

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.myhairdiary_c.main.Home2

class logo_home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo_home)
        // 앱 시작시 잠깐 나오는 커버 입니다.



        val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message?) {  // 실행이 끝난후 확인 가능
            }
        }

        Handler().postDelayed(Runnable
        {
            var intent= Intent(this, Home2::class.java)
            startActivity(intent)
            //  var a=wvnaver.
            // 실행할 동작 코딩
            mHandler.sendEmptyMessage(0) // 실행이 끝난후 알림
        }, 500 // 0.5초후에 Home2로 이동합니다.
        )




    }    override fun onBackPressed() {
        super.onBackPressed()
    }
}
