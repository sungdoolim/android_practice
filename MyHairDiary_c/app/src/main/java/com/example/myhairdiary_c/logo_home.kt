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



        val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message?) {  // 실행이 끝난후 확인 가능
            }
        }

        Handler().postDelayed(Runnable
        // 1 초 후에 실행
        {          var intent= Intent(this, Home2::class.java)
            startActivity(intent)

            //  var a=wvnaver.
            // 실행할 동작 코딩
            mHandler.sendEmptyMessage(0) // 실행이 끝난후 알림
        }, 500
        )




    }    override fun onBackPressed() {
        super.onBackPressed()
    }
}
