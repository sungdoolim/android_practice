package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find6page.*

class find6page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find6page)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        findnext6.setOnClickListener(){
            var demand=demand_et.text.toString()
            edit.putString("demand",demand)
            edit.apply()

            var intent= Intent(this, find7page::class.java)
            startActivity(intent)
        }
    }
}
