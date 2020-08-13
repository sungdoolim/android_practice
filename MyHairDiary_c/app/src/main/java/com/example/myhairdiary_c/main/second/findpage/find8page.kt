package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.detailedDesigner
import com.example.myhairdiary_c.firedb.fireDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_find8page.*

class find8page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find8page)
        val prefselected=getSharedPreferences("selected",0)

        val db= fireDB(this)
        select_designer_list(db.firestore)
        var url=prefselected.getString("profile","")
        var did=prefselected.getString("did","")
//intent.getStringExtra("profile")
        Glide.with(this).load(url).into(designer_photo)
        designer_photo.setBackground(ShapeDrawable(OvalShape()));
        designer_photo.setClipToOutline(true)
        designer_photo.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)
        }



    }
    public fun select_designer_list(firestore: FirebaseFirestore) {

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }


                    findcomplete_designer.addItemDecoration(
                        DividerItemDecoration(applicationContext,
                            DividerItemDecoration.HORIZONTAL
                        )
                    )// 경계선 추가!!!!


                    findcomplete_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    findcomplete_designer.setHasFixedSize(true)
                    findcomplete_designer.adapter=
                        find7page.findcompleteAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }

}