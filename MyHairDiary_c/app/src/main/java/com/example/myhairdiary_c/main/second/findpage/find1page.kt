package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_find1page.*

class find1page : AppCompatActivity() {
// 선택한 성별에 따른 페이지 이동이 이루어 집니다
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find1page)
        gender_radio.check(R.id.gender_radio1)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()



        findnext1.setOnClickListener(){
            var id=gender_radio.checkedRadioButtonId
            val radiotext=findViewById<RadioButton>(id)
            var value=radiotext.text.toString()
            Toast.makeText(this,value,Toast.LENGTH_SHORT).show()
            edit.putString("gender",value)
            edit.apply()

            when(id){
                R.id.gender_radio1->{
                    var intent= Intent(this, find2page_woman::class.java)
                    startActivity(intent)
                }
                else->{
                    var intent= Intent(this, find2page_man::class.java)
                    startActivity(intent)
                }
            }

        }
    }

}
