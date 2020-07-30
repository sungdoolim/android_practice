package com.example.myhairdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.myhairdiary.calendar.EditActivity
import com.example.myhairdiary.calendar.mainpage
import com.example.myhairdiary.designers.designers_profile
import com.example.myhairdiary.social.navermaps
import com.example.myhairdiary.register.Register
import com.google.android.material.navigation.NavigationView


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.mypage


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        print("hi")
        //var id=intent.getStringExtra("id")
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
        print("session : ${sesid}")
        testid.text=sesid



        register.setOnClickListener(){
            var intent= Intent(this, Register::class.java)
            startActivity(intent)

        }
        calendar.setOnClickListener(){
            var intent= Intent(this, mainpage::class.java)//mainpage
            startActivity(intent)
        }
        Rcalendar.setOnClickListener(){
            var intent= Intent(this, EditActivity::class.java)//mainpage
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
        //    var intent= Intent(this, NMAP::class.java)
        //    startActivity(intent)
        }
        testmain.setOnClickListener(){
            var intent= Intent(this, testMain::class.java)
            startActivity(intent)
        }
        nlogin.setOnClickListener(){

            var intent= Intent(this, NLogin::class.java)
            startActivity(intent)

        }
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
return true
    }
}
