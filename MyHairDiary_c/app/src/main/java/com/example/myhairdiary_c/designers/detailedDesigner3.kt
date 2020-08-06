package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner3 : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer3)
        bt3.setBackgroundColor(R.color.colorAccent)
        val prefselected=getSharedPreferences("selected",0)
        var did=prefselected.getString("did","")
        bt1.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)

        }
        bt2.setOnClickListener(){
            var intent= Intent(this, detailedDesigner2::class.java)
            startActivity(intent)
        }
        bt4.setOnClickListener(){
            var intent= Intent(this, detailedDesigner4::class.java)
            startActivity(intent)
        }

    }
}
