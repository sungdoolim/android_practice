package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find6page.*

class find6page : AppCompatActivity() {
// 유저의 요청 사항 -> 밑 코드와 같이 데이터는 받지만 아직 데이터를 활요하지 않았습니다.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find6page)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
    val prefsession=getSharedPreferences("session",0)
    textView24.text=prefsession.getString("id","").toString()+" 님의 문의 및 희망사항을 기재해 주세요!"
        findnext6.setOnClickListener(){
            var demand=demand_et.text.toString()
            edit.putString("demand",demand)
            edit.apply()

            var intent= Intent(this, find7page::class.java)
            startActivity(intent)
        }
    }
}
