package com.example.myhairdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mypage.*

class Mypage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        var firestore = FirebaseFirestore.getInstance()
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
        firestore.collection("hair_diary").whereEqualTo("id",sesid).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        var userDTO =dc.toObject(designer::class.java)
                        myid.text=userDTO?.id
                        mymemo.text=userDTO?.memo
                        myyear.text=userDTO?.year.toString()


                        println("success ${userDTO.toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }



                }else{
                    println("fail")
                }
            }



    }
}
