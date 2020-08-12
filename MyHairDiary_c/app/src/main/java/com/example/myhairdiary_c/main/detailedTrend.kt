package com.example.myhairdiary_c.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_detailed_recommend.*
import kotlinx.android.synthetic.main.activity_detailed_trend.*

class detailedTrend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_trend)
        val pref=getSharedPreferences("recommended",0)
        val url=pref.getString("url","")
        Glide.with(this).load(url).into(selected_trend)
    }
}
