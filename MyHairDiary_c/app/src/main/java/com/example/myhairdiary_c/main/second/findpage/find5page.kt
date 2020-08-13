package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find5page.*

class find5page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find5page)
        val items = arrayOf("서울","경기","인천","대전","대구")
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        var value:String=""
        spinner.adapter=myAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                value=items[position]
                Toast.makeText(this@find5page,value,Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        findnext5.setOnClickListener(){
            edit.putString("region",value)
            edit.apply()
            var intent= Intent(this, find6page::class.java)
            startActivity(intent)
        }
    }
}
