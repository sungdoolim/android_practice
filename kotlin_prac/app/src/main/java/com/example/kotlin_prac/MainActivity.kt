package com.example.kotlin_prac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.setText("hi")
        bt1.setOnClickListener(){

            var str=et.getText()
            tv.setText(str)
            println("--------------------------------------------------------------------------버튼 활용-------${et.getText()}")
        }
        bt2.setOnClickListener(){
            var intent=Intent(this,Main2Activity::class.java)
            intent.putExtra("intentvalue","hi second?")
            startActivity(intent)

        }
        bt3.setOnClickListener(){
            var intent=Intent(this,Main3Activity::class.java)
            startActivity(intent)
        }


    }
}
