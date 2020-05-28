package com.example.kotlin_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main5.*

class Main5Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val profileList=arrayListOf(
            profiles(R.drawable.ic_launcher_foreground,"staris",25,"견습 웹/앱"),
            profiles(R.drawable.ic_launcher_foreground,"hama   ",23," 웹/앱"),
            profiles(R.drawable.ic_attach_money_black_24dp,"gagueng",22,"플루터"),
            profiles(R.drawable.ic_launcher_background,"sieun",21,"냐옹")
        )


        rv_profile.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv_profile.setHasFixedSize(true)
        rv_profile.adapter=ProfilesAdapter(profileList)



    }
}
