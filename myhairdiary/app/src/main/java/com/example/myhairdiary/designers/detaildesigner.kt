package com.example.myhairdiary.designers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary.R
import com.example.myhairdiary.social.faceB
import com.example.myhairdiary.social.insta
import com.example.myhairdiary.social.navermaps
import com.example.myhairdiary.social.youtube
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import kotlinx.android.synthetic.main.activity_portfolio.*

class detaildesigner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaildesigner)
        val age=intent.getStringExtra("age")?:"null"
        val name=intent.getStringExtra("name")?:"null"
        val year=intent.getStringExtra("year")?:"null"
        val memo=intent.getStringExtra("memo")?:"null"
        val phone=intent.getStringExtra("phone")?:"null"
        val index : Int=Integer.parseInt(intent.getStringExtra("index")?:"0")
        print("index : ! ${index}")
        val id=intent.getStringExtra("id")?:"null"
        println(age)
        println(name)
        println(year)
        println(memo)
        detailage.text=age;
        datailname.text=name;
        detailyear.text=year;
        detailmemo.text=memo;
        detailid.text=id;

        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore,id,index)// 리사이클러뷰 띄우기
        //selectList2(firestore)
// 얘는 원래 recycler뷰를 적용하려했는데...



        gotonaver.setOnClickListener(){
            val intent = Intent(this, navermaps::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        gotoface.setOnClickListener(){
            val intent = Intent(this, faceB::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        gotoyoutube.setOnClickListener(){
            val intent = Intent(this, youtube::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        gotoinsta.setOnClickListener(){
            val intent = Intent(this, insta::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        adpt_list.setOnClickListener(){
            val intent = Intent(this, dimg_adpt_list::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }
        trace.setOnClickListener(){

            val intent = Intent(this, Tracking::class.java)
            intent.putExtra("id",id)
            intent.putExtra("index",index.toString())
            startActivity(intent)


        }
        print("intent!!!")
    }
    fun loadPhoto(downloadUrl : String,i :Int) {
        when(i){
            0->  Glide.with(this).load(downloadUrl).into(testimgview0)
            1->  Glide.with(this).load(downloadUrl).into(testimgview1)
            2->  Glide.with(this).load(downloadUrl).into(testimgview2)
            3->  Glide.with(this).load(downloadUrl).into(testimgview3)
            4->  Glide.with(this).load(downloadUrl).into(testimgview4)
            5->  Glide.with(this).load(downloadUrl).into(testimgview5)
            6->  Glide.with(this).load(downloadUrl).into(testimgview6)
            7->  Glide.with(this).load(downloadUrl).into(testimgview7)
            8->  Glide.with(this).load(downloadUrl).into(testimgview8)
            9->  Glide.with(this).load(downloadUrl).into(testimgview4)
            10->  Glide.with(this).load(downloadUrl).into(testimgview5)
            11->  Glide.with(this).load(downloadUrl).into(testimgview6)
            12->  Glide.with(this).load(downloadUrl).into(testimgview7)
            13->  Glide.with(this).load(downloadUrl).into(testimgview8)
            14->  Glide.with(this).load(downloadUrl).into(testimgview7)
            15->  Glide.with(this).load(downloadUrl).into(testimgview8)



            else->""

        }
       // Glide.with(this).load(downloadUrl).into(testimgview1)
    }
    public fun selectList2(firestore:FirebaseFirestore) {
        println("read")
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
        var max: Int = Integer.parseInt(pref.getString("index","0")!!)

        val profileList=ArrayList<Dimgs>()
        //    Dimgs(a,b,a),Dimgs(b,a,b),Dimgs(a,b,a)

        var url1=""
        var url2=""
        var url3=""

        for(i in 0..max-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images")
                .child(sesid + "_." + i.toString())
            println({"i : ${i},max : ${max}"})
            Log.d("", "i : ${i},max : ${max}")
           // println("dwurl : ${storageRef.downloadUrl}")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
              //  println("dwrul2 : ${uri}")

                //   loadPhoto(uri.toString(),i)

                println("i:${i}")
                when((i)%3){
                    0->{url1=uri.toString()
                        println(url1)
                    }
                    1->{url2=uri.toString()
                        println(url2)
                    }
                    2->{url3=uri.toString()
                    profileList.add(Dimgs(url1,url2,url3))
                            ""}
                  else->""

                }
            }


        }
        if((max-1)/3!=2){
            profileList.add(Dimgs(url1,url2,url3))
        }
       // profileList.add(Dimgs(url1,url2,url3))

        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?" +
                "alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
        var b="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.1?" +
                "alt=media&token=bc8a1336-2872-488c-8808-ea540f46cda1"
        var c="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.1?" +
                "alt=media&token=bc8a1336-2872-488c-8808-ea540f46cda1"




      //  profileList.add(Dimgs(a,b,c))
        // dimglistrv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)







//        designerimg_rv.setHasFixedSize(true)
//        val mLayoutManager =  LinearLayoutManager(this);
//        designerimg_rv.layoutManager = mLayoutManager;
//        designerimg_rv.adapter=dimgAdapter(this,profileList)

        println("read end")

    }









    public fun selectList(firestore:FirebaseFirestore,id:String,index:Int) {
        println("read")



        val pref=getSharedPreferences("ins",0)
        //var sesid=pref.getString("id","null")
       // var max: Int = Integer.parseInt(pref.getString("index","0")!!)
       // Log.d(""," max : ${max}")
        //println("max : ${max}")


        for(i in 0..index-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images")
                .child(id + "_." + i.toString())
            println(i)
            println("dwurl : ${storageRef.downloadUrl}")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                println("dwrul2 : ${uri}")
                loadPhoto(uri.toString(),i)
                println("i:${i}")
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
}
