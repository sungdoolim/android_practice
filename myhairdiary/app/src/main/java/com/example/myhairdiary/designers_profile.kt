package com.example.myhairdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.activity_designers_profile.*

class designers_profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designers_profile)


        val designerL=arrayListOf(

            designer(R.drawable.ic_launcher_foreground,"1",1,"1111",25,"열심히 하겠습니다"),
            designer(R.drawable.ic_launcher_foreground,"2",1,"2222",25,"열심히 하겠습니다"),
            designer(R.drawable.ic_launcher_foreground,"3",1,"3333",25,"열심히 하겠습니다"),
            designer(R.drawable.ic_launcher_foreground,"4",1,"4444",25,"열심히 하겠습니다")

        )


        designerlist.layoutManager=
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        designerlist.setHasFixedSize(true)
        designerlist.adapter= designerAdapter(designerL)



    }
}
