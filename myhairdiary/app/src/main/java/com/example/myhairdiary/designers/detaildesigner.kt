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
        selectList(firestore)
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
            else->""

        }
       // Glide.with(this).load(downloadUrl).into(testimgview1)
    }

    public fun selectList(firestore:FirebaseFirestore) {
        println("read")



        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
        var max: Int = pref.getInt("index",0)
        Log.d(""," max : ${max}")
        println("max : ${max}")


        for(i in 0..max-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images")
                .child(sesid + "_." + i.toString())
            println(i)
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                loadPhoto(uri.toString(),i)
                println("i:${i}")
            }
        }




        val profileList=arrayListOf(
            Dimgs(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground),
            Dimgs(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground),
            Dimgs(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground)

        )


        // dimglistrv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        designerimg_rv.setHasFixedSize(true)
        val mLayoutManager =  LinearLayoutManager(this);
        designerimg_rv.layoutManager = mLayoutManager;
        designerimg_rv.adapter=dimgAdapter(profileList)

        println("read end")

    }
}
