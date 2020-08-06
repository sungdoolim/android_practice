package com.example.myhairdiary_c.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designerAdapter
import com.example.myhairdiary_c.designers.designer_list
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.fireDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_recommend.*

class detailedRecommend : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_recommend)

        val prefrecommend=getSharedPreferences("recommended",0)
        val edit=prefrecommend.edit()
        var did=prefrecommend.getString("did","")
        var url=prefrecommend.getString("url","")
        Glide.with(this).load(url.toString()).into(selected_style_img)
        var db= fireDB(this)
        select_wecando(db.firestore,did!!)
        select_another_style(db.firestore,did!!)
        overview_recom.setOnClickListener(){


            var intent= Intent(this, designer_list::class.java)

            startActivity(intent)
        }

    }
    fun select_wecando(firestore:FirebaseFirestore,did:String){
        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    //    println("userdto len = ${userDTO.size}")
                    wecando_rv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    wecando_rv.setHasFixedSize(true)
                    wecando_rv.adapter=
                        designerAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
    fun select_another_style(firestore:FirebaseFirestore,did:String){
        firestore?.collection("hair_photo").whereEqualTo("id",did).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    //    println("userdto len = ${userDTO.size}")
                    another_rv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    another_rv.setHasFixedSize(true)
                    another_rv.adapter=
                        recommend_trend_adapter(
                            this,
                            userDTO,3
                        )
                }else{
                    println("fail")
                }
            }
    }

}

