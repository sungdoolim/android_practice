package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find4page.*

class find4page : AppCompatActivity() {
//컷 종류 선택
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find4page)
        kinds_radio.check(R.id.kinds_radio1)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
    val prefsession=getSharedPreferences("session",0)
    textView24.text=prefsession.getString("id","")+" 님이 중요하게 생각하는 시술은 어떤 것이 있을까요?"
        findnext4.setOnClickListener(){
            var id=kinds_radio.checkedRadioButtonId
            val radiotext=findViewById<RadioButton>(id)
            val value=radiotext.text.toString()
            edit.putString("kind",value)
            edit.apply()


            Toast.makeText(this,value, Toast.LENGTH_SHORT).show()
            var intent= Intent(this, find5page::class.java)
            startActivity(intent)
        }
    }
}
