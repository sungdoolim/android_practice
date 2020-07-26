package com.example.myhairdiary.designers.ReView

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_review.*

class Review : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        var did=intent.getStringExtra("id")
        var reviewcount=intent.getIntExtra("reviewcount",-1)
        println("did : ${did} reviewcount : ${reviewcount}")
        writeReview.setOnClickListener(){
            val intent = Intent(this, write_review::class.java)
            intent.putExtra("id",did)
            intent.putExtra("reviewcount",reviewcount)
            startActivity(intent)
        }

        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore,did)
    }
    public fun selectList(firestore:FirebaseFirestore,did:String="") {

        firestore?.collection("hair_review").whereEqualTo("designer_id",did).get()
            .addOnCompleteListener {
                var len=0
                if(it.isSuccessful){
                    println("review")
                    var userDTO=ArrayList<review_data>()
                    for(dc in it.result!!.documents){



                        //  println("${len+1} : ${dc.toString()}")
                        dc.toObject(review_data::class.java)?.let { it1 -> userDTO.add(it1) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        len++
                    }
                    reviewList.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    reviewList.setHasFixedSize(true)
                    reviewList.adapter=
                        reviewAdapter(
                            this,
                            userDTO
                        )
                    //print("select list clear")
                    len=0
                }else{
                    println("fail")
                }
            }
        println("read end")
    }
}
