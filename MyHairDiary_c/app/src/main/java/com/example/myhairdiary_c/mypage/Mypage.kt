package com.example.myhairdiary_c.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.fireDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_mypage.*

class Mypage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        val db= fireDB(this)
        val firestore=db.firestore
        //   myfav_designer /   myfav_style
        select_mystyle(firestore)
        select_mydesigner(firestore)
        gotomypage2.setOnClickListener(){

            var intent= Intent(this, Mypage2::class.java)
            startActivity(intent)

        }
    }
    public fun select_mydesigner(firestore: FirebaseFirestore) {// 지금은 recommend리스트랑 똑같음 // 얘가 맨 마지막애인가바

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    println("designers  len = ${userDTO.size}")

                    // recommend_designer_list 는 id로 얻어온 recyclerview 임

                    myfav_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    myfav_designer.setHasFixedSize(true)
                    myfav_designer.adapter=
                        heart_mydesignerAdapter(this,userDTO)
                }else{
                    println("fail")
                }
            }
    }

    fun select_mystyle(firestore: FirebaseFirestore) {// 지금은 recommend리스트랑 똑같음 // 얘가 맨 마지막애인가바

        firestore?.collection("hair_photo").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    println("designers  len = ${userDTO.size}")

                    // recommend_designer_list 는 id로 얻어온 recyclerview 임

                    myfav_style.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    myfav_style.setHasFixedSize(true)
                    myfav_style.adapter=
                        heart_mystyleAdapter(this,userDTO)
                }else{
                    println("fail")
                }
            }
    }

}
