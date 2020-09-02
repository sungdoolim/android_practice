package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find2page_woman.*

class find2page_woman : AppCompatActivity() {
    //성별에 따라 길이가 달라집니다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find2page_woman)
        length_radio_woman.check(R.id.length_radio1_woman)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        val prefsession=getSharedPreferences("session",0)
        textView24.text=prefsession.getString("id","").toString()+" 님의 머리 길이는 어떤가요?"
        findnext2_woman.setOnClickListener(){
            var id=length_radio_woman.checkedRadioButtonId
            val radiotext=findViewById<RadioButton>(id)
            val value=radiotext.text.toString()
            edit.putString("length",value)
            edit.apply()
            Toast.makeText(this,value, Toast.LENGTH_SHORT).show()

            var intent= Intent(this, find4page::class.java)
            startActivity(intent)
        }
    }
}
