package com.example.myhairdiary.calendar

import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.myhairdiary.R

import kotlinx.android.synthetic.main.activity_calbasic.*
import kotlinx.android.synthetic.main.content_calbasic.*

class CalbasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calbasic)
        setSupportActionBar(toolbar)

        var subitem=arrayOf("dd","22","dfsjl",1232,R.drawable.ic_launcher_background)
        var item=arrayOf(3,5,subitem,'q')
        todolistview.adapter=ArrayAdapter<Any>(this,android.R.layout.simple_list_item_1,item)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}
