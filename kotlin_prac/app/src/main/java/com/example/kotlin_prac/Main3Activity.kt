package com.example.kotlin_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {
    var Userl= arrayListOf<Users>(Users(R.drawable.ic_launcher_background,"staris",19),
        Users(R.drawable.ic_launcher_foreground,"hama",24))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
//
//        var item=arrayOf(3,5,"star",'q')
//        lv.adapter=ArrayAdapter<Any>(this,android.R.layout.simple_list_item_1,item)

        val adpt=customAdpt(this,Userl)
        lv.adapter=adpt






    }
}
