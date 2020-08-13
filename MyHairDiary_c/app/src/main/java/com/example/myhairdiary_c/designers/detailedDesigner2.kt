package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_designer2.*
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner2 : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer2)
       // bt2.setBackgroundColor(R.color.colorAccent)
        val prefselected=getSharedPreferences("selected",0)
        var did=prefselected.getString("did","")
        bt1.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)

        }
        bt3.setOnClickListener(){
            var intent= Intent(this, detailedDesigner3::class.java)
            startActivity(intent)
        }
        bt4.setOnClickListener(){
            var intent= Intent(this, detailedDesigner4::class.java)
            startActivity(intent)
        }

val db= fireDB(this)
        select_hair(db.firestore,did!!) // recommend 리스트 출력


    }

    public fun select_hair(firestore: FirebaseFirestore,did:String) {// 지금은 recommend리스트랑 똑같음

        firestore?.collection("hair_photo").whereEqualTo("id",did).get()
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

                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=
                        photorvAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}
