package com.example.myhairdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.portfolio
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mypage.*

class Mypage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        var firestore = FirebaseFirestore.getInstance()
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
        var permis=pref.getString("perm","null")
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
                if(!permis.equals("null")) {
                    var per = Integer.parseInt(permis.toString())
                    print("성공!!!!!!!!!!!!!!!!!!!!!!!!${per}")
                    Log.d("d","성공!!!!!!!!!!!!!!!!!!!!!!!!${per}.........")
                    when (per) {

                        1 -> designer_bt.visibility = View.VISIBLE;

                        2 -> designer_bt.visibility = View.VISIBLE;

                        else -> designer_bt.visibility = View.INVISIBLE;
                    }
                }else{designer_bt.visibility = View.INVISIBLE;
                    print("실패!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1")
                    Log.d("d","실패.........")
                }
                designer_bt.setOnClickListener(){
                    var intent= Intent(this, portfolio::class.java)
                    startActivity(intent)
                }
            }







    }
}
