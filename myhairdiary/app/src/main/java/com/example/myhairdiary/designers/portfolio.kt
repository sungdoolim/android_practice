package com.example.myhairdiary.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myhairdiary.R
import com.example.myhairdiary.designer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_portfolio.*

class portfolio : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)
        modify.setOnClickListener(){
            var memo=Modmemo.text
            var year=Modyear.text

            val pref=getSharedPreferences("ins",0)

            var sesid=pref.getString("id","null")
            Log.d("id````````````````````",sesid)

            var firestore = FirebaseFirestore.getInstance()
            updateData(firestore,memo.toString(),Integer.parseInt(year.toString()),sesid.toString())
        }


    }
    public fun updateData(firestore:FirebaseFirestore,memo:String,year:Int,sesid:String){// 잘됨
             firestore?.collection("hair_diary").whereEqualTo("id",sesid).get()
            .addOnCompleteListener {

                var len=0
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    Log.d("success","success update........................................")
                    for(dc in it.result!!.documents){
                        //  println("${len+1} : ${dc.toString()}")
                        dc.toObject(designer::class.java)?.let { it1 -> userDTO.add(it1) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        var map= mutableMapOf<String,Any>()
                        map["memo"] =memo

                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{

                                    Log.d("fail","fail update........................................1")
                                }
                            }
                        map["year"]=year
                        //Log.d("1","${it.result!!.documents[0].toString()}")
                        //Log.d("11","${it.result!!.documents[0]["ref"].toString()}")
                        //Log.d("11","${it.result!!.documents[0].reference.toString()}")
                       // Log.d("11","${it.result!!.documents[0].metadata.toString()}")
                        Log.d("11","${it.result!!.documents[0].id.toString()}")
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{

                                    Log.d("fail","fail update........................................2")
                                }
                            }

                    }
                }else{
                    Log.d("fail","fail update........................................")
                    println("fail")
                }


            }






    }
}
