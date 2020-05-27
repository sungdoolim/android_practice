package com.example.kotlin_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        tv.text="secon"
            tv.text=intent.getStringExtra("intentvue")?:"null?"
        bt3.setOnClickListener(){
            img.setImageResource(R.drawable.ic_launcher_foreground)
            Toast.makeText(this,"toast test",Toast.LENGTH_SHORT).show()

        }
    }
}
