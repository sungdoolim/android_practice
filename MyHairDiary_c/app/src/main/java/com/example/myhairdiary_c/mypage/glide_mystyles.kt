package com.example.myhairdiary_c.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.style.MyAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_glide_mystyles.*

class glide_mystyles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glide_mystyles)
        val db= fireDB(this)
        val pref=getSharedPreferences("session",0)
        val id=pref.getString("id","").toString()
        sort_submit.setOnClickListener(){
            if(fav_sort.isChecked){


            }

            if(near_sort.isChecked){


            }

            if(review_sort.isChecked){


            }


        }


        selectList(db.firestore,id)
    }
    public fun selectList(firestore: FirebaseFirestore, id:String) {

        firestore?.collection("hair_mystyle").whereEqualTo("customid",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->

                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }

                    var adapter = MyAdapter(
                        applicationContext,
                        R.layout.search_grid_adapter,  // GridView 항목의 레이아웃 row.xml
                        userDTO
                    )
                    mystyle_list_grid.adapter = adapter
                    mystyle_list_grid.setOnItemClickListener { parent, view, position, id ->
                        mystyle_list_grid.adapter.getItem(position)
                    }
                }else{
                    println("fail")
                }
            }
    }
}
