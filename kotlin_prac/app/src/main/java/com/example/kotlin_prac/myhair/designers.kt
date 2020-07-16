package com.example.kotlin_prac.myhair

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin_prac.ProfilesAdapter
import com.example.kotlin_prac.R
import com.example.kotlin_prac.profiles
import kotlinx.android.synthetic.main.activity_designers.*
import kotlinx.android.synthetic.main.activity_main5.*

class designers: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designers)

        val profileList=arrayListOf(
            profiles(R.drawable.ic_launcher_foreground,"1111",25,"열심히 하겠습니다"),
            profiles(R.drawable.ic_launcher_foreground,"2222   ",23," 잘 하겠습니다"),
            profiles(R.drawable.ic_attach_money_black_24dp,"3333",22,"알아서 하겠습니다"),
            profiles(R.drawable.ic_launcher_background,"44444",21,"대충 하겠습니다")
        )


        designers_profile.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        designers_profile.setHasFixedSize(true)
        designers_profile.adapter= ProfilesAdapter(profileList)



    }



}
