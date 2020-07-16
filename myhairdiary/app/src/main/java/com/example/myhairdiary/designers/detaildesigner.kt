package com.example.myhairdiary.designers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary.R
import com.example.myhairdiary.social.faceB
import com.example.myhairdiary.social.insta
import com.example.myhairdiary.social.navermaps
import com.example.myhairdiary.social.youtube
import kotlinx.android.synthetic.main.activity_detaildesigner.*

class detaildesigner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaildesigner)
        val age=intent.getStringExtra("age")?:"null"
        val name=intent.getStringExtra("name")?:"null"
        val year=intent.getStringExtra("year")?:"null"
        val memo=intent.getStringExtra("memo")?:"null"
        val phone=intent.getStringExtra("phone")?:"null"
        val id=intent.getStringExtra("id")?:"null"
        println(age)
        println(name)
        println(year)
        println(memo)
        detailage.text=age;
        datailname.text=name;
        detailyear.text=year;
        detailmemo.text=memo;
        detailid.text=id;
        gotonaver.setOnClickListener(){
            val intent = Intent(this, navermaps::class.java)
            startActivity(intent)
        }
        gotoface.setOnClickListener(){
            val intent = Intent(this, faceB::class.java)
            startActivity(intent)
        }
        gotoyoutube.setOnClickListener(){
            val intent = Intent(this, youtube::class.java)
            startActivity(intent)
        }
        gotoinsta.setOnClickListener(){
            val intent = Intent(this, insta::class.java)
            startActivity(intent)
        }
        print("intent!!!")
    }
}
