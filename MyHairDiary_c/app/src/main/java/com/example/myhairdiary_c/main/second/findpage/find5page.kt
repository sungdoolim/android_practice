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
        val items = arrayOf("선택 하기","전 지역","서울","경기","인천","대전","대구")
        val items2 = arrayOf("선택 하기","전 지역","강남구","강동구","강북구","강서구","관악구","광진구","구로구","금천구","노원구","도봉구",
            "동대문구","동작구","마포구","서대문구","서초구","성동구","성북구","송파구","양천구","영등포구",
            "용산구","은평구","종로구","중구","중랑구")
        val default=arrayOf("선택 하기","전 지역")
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        var myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        var myAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items2)

        var value:String=""
        var value2:String=""
        spinner.adapter=myAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                value=items[position]
                Toast.makeText(this@find5page,value,Toast.LENGTH_SHORT).show()
                if(value=="서울"){

                    myAdapter = ArrayAdapter(this@find5page, android.R.layout.simple_spinner_dropdown_item, items2)

                    spinner2.adapter=myAdapter
                }else{

                    myAdapter = ArrayAdapter(this@find5page, android.R.layout.simple_spinner_dropdown_item, default)
                    spinner2.adapter=myAdapter
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        spinner2.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                value2="전 지역"
            }

            override fun onItemSelected( parent: AdapterView<*>?,view: View?, position: Int, id: Long) {
                value2=items2[position]
            }

        }
        findnext5.setOnClickListener(){
            edit.putString("region",value)
            edit.putString("region2",value2)
            edit.apply()
            var intent= Intent(this, find6page::class.java)
            startActivity(intent)
        }
    }
}
