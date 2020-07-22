package com.example.myhairdiary.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import kotlinx.android.synthetic.main.activity_tracking.*

class Tracking : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        var firestore=FirebaseFirestore.getInstance()
        var pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","")
        var id=intent.getStringExtra("id")
        var index=Integer.parseInt(intent.getStringExtra("index"))
        selectList(firestore,id,index,sesid!!)
    }
    fun loadPhoto(downloadUrl : String,i :Int) {
        when(i){
            0->  Glide.with(this).load(downloadUrl).into(trac_image1)
            1->  Glide.with(this).load(downloadUrl).into(trac_image2)
            2->  Glide.with(this).load(downloadUrl).into(trac_image3)
            3->  Glide.with(this).load(downloadUrl).into(trac_image4)
            4->  Glide.with(this).load(downloadUrl).into(trac_image5)
            5->  Glide.with(this).load(downloadUrl).into(trac_image6)
            6->  Glide.with(this).load(downloadUrl).into(trac_image7)
            7->  Glide.with(this).load(downloadUrl).into(trac_image8)
            8->  Glide.with(this).load(downloadUrl).into(trac_image9)
            9->  Glide.with(this).load(downloadUrl).into(trac_image10)
            10->  Glide.with(this).load(downloadUrl).into(trac_image11)
            11->  Glide.with(this).load(downloadUrl).into(trac_image12)
            12->  Glide.with(this).load(downloadUrl).into(trac_image13)
            13->  Glide.with(this).load(downloadUrl).into(trac_image14)
            14->  Glide.with(this).load(downloadUrl).into(trac_image15)
            else->""
        }
    }
    public fun selectList(firestore: FirebaseFirestore, id:String, index:Int,customid:String) {
        firestore?.collection("hair_diary").whereEqualTo("id",id.toString()).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        if(dc.get(customid)==null){
                        }else{
                            var count=Integer.parseInt(dc.get(customid)!!.toString())
                            for(i in 0..count-1){
                                var storageRef = FirebaseStorage.getInstance().reference.child("images")
                                    .child(id +"_"+customid+ "_." + i.toString())
                                println("exception? : ${storageRef.toString()}")
                                println(i)
                                println("dwurl : ${storageRef.downloadUrl}")
                                storageRef.downloadUrl.addOnSuccessListener { uri ->
                                    println("dwrul2 : ${uri}")
                                    loadPhoto(uri.toString(),i)
                                    println("i:${i}")
                                }
                            }
                        }
                    }
                }else{
                    println("fail")
                }
            }
    }
    public fun updateData(firestore:FirebaseFirestore,youtubeurl:String,facebookurl:String,instaurl:String,memo:String,year:Int,sesid:String){// 잘됨
        firestore?.collection("hair_diary").whereEqualTo("id",sesid).get()
            .addOnCompleteListener {
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
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{
                                    Log.d("fail","fail update........................................2")
                                }
                            }
                        map["youurl"]=youtubeurl
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{
                                    Log.d("fail","fail update........................................2")
                                }
                            }
                        map["faceurl"]=facebookurl
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{
                                    Log.d("fail","fail update........................................2")
                                }
                            }
                        map["instaurl"]=instaurl
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
