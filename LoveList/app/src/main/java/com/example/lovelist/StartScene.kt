package com.example.lovelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.frag.home

class StartScene : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_scene)
        val transaction=supportFragmentManager.beginTransaction()
        val f1= home()
        transaction.replace(R.id.framelayout,f1)
        transaction.commit()
    }
}
