package com.example.myhairdiary_c.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myhairdiary_c.R
import kotlinx.android.synthetic.main.activity_mypage2.*

class Mypage2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage2)
        mypage2_save.setOnClickListener(){
            var iscpt=mypage2_cptbox.isChecked
            println(iscpt)
            if(iscpt){
            Toast.makeText(this,"체크됨",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"체크 해제됨",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
