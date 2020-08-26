package com.example.myhairdiary_c.diary

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_my_board.*

class MyBoard : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_board)
        var a=0
        myboard_all.setOnClickListener(){
            a=0
            myboard_all.setTextColor(Color.parseColor("#A9E3FD"))


            myboard_neighb.setTextColor(Color.BLACK)
            myboard_neineigh.setTextColor(Color.BLACK)
            myboard_private.setTextColor(Color.BLACK)
            Toast.makeText(this,"position "+a,Toast.LENGTH_SHORT).show()
        }
        myboard_neighb.setOnClickListener(){
            a=1
     //       myboard_all.setBackgroundColor(Color.parseColor("#FFFFFF"))

            myboard_all.setTextColor(Color.BLACK)
            myboard_neighb.setTextColor(Color.parseColor("#A9E3FD"))
            myboard_neineigh.setTextColor(Color.BLACK)
            myboard_private.setTextColor(Color.BLACK)
            Toast.makeText(this,"position "+a,Toast.LENGTH_SHORT).show()
        }
        myboard_neineigh.setOnClickListener(){
            a=2

            myboard_all.setTextColor(Color.BLACK)
            myboard_neighb.setTextColor(Color.BLACK)
            myboard_neineigh.setTextColor(Color.parseColor("#A9E3FD"))
            myboard_private.setTextColor(Color.BLACK)
            Toast.makeText(this,"position "+a,Toast.LENGTH_SHORT).show()
        }
        myboard_private.setOnClickListener(){
            a=3

            myboard_all.setTextColor(Color.BLACK)
            myboard_neighb.setTextColor(Color.BLACK)
            myboard_neineigh.setTextColor(Color.BLACK)
            myboard_private.setTextColor(Color.parseColor("#A9E3FD"))
            Toast.makeText(this,"position "+a,Toast.LENGTH_SHORT).show()
        }
    }
}