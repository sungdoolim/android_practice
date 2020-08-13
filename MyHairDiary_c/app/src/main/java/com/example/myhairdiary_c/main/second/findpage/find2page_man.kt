package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find2page_man.*

class find2page_man : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find2page_man)
        length_radio_man.check(R.id.length_radio1_man)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        findnext2_man.setOnClickListener(){
            var id=length_radio_man.checkedRadioButtonId
            val radiotext=findViewById<RadioButton>(id)
            val value=radiotext.text.toString()
            Toast.makeText(this,value,Toast.LENGTH_SHORT).show()
            edit.putString("length",value)
            edit.apply()
            var intent= Intent(this, find4page::class.java)
            startActivity(intent)
        }
    }
}