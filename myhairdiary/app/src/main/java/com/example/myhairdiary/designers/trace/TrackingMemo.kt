package com.example.myhairdiary.designers.trace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_tracking_memo.*

class TrackingMemo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking_memo)
        var designerid=intent.getStringExtra("did")
        var pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","")

        var firestore=FirebaseFirestore.getInstance()
        selectList(firestore,sesid!!,designerid)

    }
    public fun selectList(firestore:FirebaseFirestore,sesid:String,designerid:String) {
        firestore?.collection("hair_feed").whereEqualTo("customid",sesid)
            .whereEqualTo("id",designerid).orderBy("count",Query.Direction.DESCENDING).get()
            .addOnCompleteListener {
                var len=0
                if(it.isSuccessful){
                    var userDTO=ArrayList<feedData>()
                    for(dc in it.result!!.documents){
                        //  println("${len+1} : ${dc.toString()}")

                        dc.toObject(feedData::class.java)?.let { it1 ->
                         userDTO.add(it1) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        len++
                    }
                    trac_re.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    trac_re.setHasFixedSize(true)
                    trac_re.adapter=
                        tracAdapter(
                            this,
                            userDTO
                        )
                    //print("select list clear")

                }else{
                    println("fail")
                }
            }
        println("read end")
    }
}
