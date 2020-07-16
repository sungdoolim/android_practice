package com.example.myhairdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary.maps.navermaps
import com.example.myhairdiary.register.Register


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.mypage


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register.setOnClickListener(){
            var intent= Intent(this, Register::class.java)
            startActivity(intent)

        }
        calendar.setOnClickListener(){
            var intent= Intent(this, mainpage::class.java)
            startActivity(intent)
        }
        mypage.setOnClickListener(){
            var intent= Intent(this, Mypage::class.java)
            startActivity(intent)
        }
        designers.setOnClickListener(){
            var intent= Intent(this, designers_profile::class.java)
            startActivity(intent)
        }
        naver_map.setOnClickListener(){
            var intent= Intent(this, navermaps::class.java)
            startActivity(intent)
        }
    }
}
