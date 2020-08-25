package com.example.myhairdiary_c.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.style.grid_adapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_glide_mydesigners.*


class glide_mydesigners : AppCompatActivity() {
// 안쓰는 건가..?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_mydesigners)
        val db= fireDB(this)
        val pref=getSharedPreferences("session",0)
        val id=pref.getString("id","").toString()
        sort_submit.setOnClickListener(){
            val fav=fav_sort.isChecked
            val near=near_sort.isChecked
            val review=review_sort.isChecked



            selectList(db.firestore,id)
        }


        selectList(db.firestore,id)

    }
    public fun selectList(firestore: FirebaseFirestore,id:String) {

        firestore?.collection("hair_mydesigner").whereEqualTo("customid",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }

                    var adapter = grid_adapter(
                        applicationContext,
                        R.layout.search_grid_adapter,  // GridView 항목의 레이아웃 row.xml
                        userDTO
                    )
                    mydesigner_list_grid.adapter = adapter
                    mydesigner_list_grid.setOnItemClickListener { parent, view, position, id ->
                        mydesigner_list_grid.adapter.getItem(position)
                    }
                }else{
                    println("fail")
                }
            }
    }
}
