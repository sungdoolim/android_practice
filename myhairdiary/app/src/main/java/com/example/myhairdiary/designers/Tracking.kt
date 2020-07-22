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
        // Glide.with(this).load(downloadUrl).into(testimgview1)
    }
    public fun selectList(firestore: FirebaseFirestore, id:String, index:Int,customid:String) {
        println("read")



        val pref=getSharedPreferences("ins",0)
        //var sesid=pref.getString("id","null")
        // var max: Int = Integer.parseInt(pref.getString("index","0")!!)
        // Log.d(""," max : ${max}")
        //println("max : ${max}")
var customidfield:String;

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






        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
        var b="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.1?alt=media&token=bc8a1336-2872-488c-8808-ea540f46cda1"

        val profileList=arrayListOf(


            Dimgs(a,b,a),Dimgs(b,a,b),Dimgs(a,b,a)


        )


        // dimglistrv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)






//        designerimg_rv.setHasFixedSize(true)
//        val mLayoutManager =  LinearLayoutManager(this);
//        designerimg_rv.layoutManager = mLayoutManager;
//        designerimg_rv.adapter=dimgAdapter(this,profileList)

        println("read end")

    }
    public fun updateData(firestore:FirebaseFirestore,youtubeurl:String,facebookurl:String,instaurl:String,memo:String,year:Int,sesid:String){// 잘됨
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
