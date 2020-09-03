package com.example.myhairdiary_c.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.firedb.fireDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_designer_list.*

class designer_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designer_list)
        val db= fireDB(this)
        select_designer_list(db.firestore) // 모든 디자이너 출력입니다.
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    public fun select_designer_list(firestore:FirebaseFirestore) {
        // 로직은 main/Home2의 select_recommend_designerList와 같습니다.
        // main/Home2의 주석을 참고하십시오

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    designerlist.addItemDecoration(
                        DividerItemDecoration(applicationContext,
                            DividerItemDecoration.VERTICAL
                        )
                    )// 경계선 추가!!!!
                    designerlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    designerlist.setHasFixedSize(true)
                    designerlist.adapter=
                        designerAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}
