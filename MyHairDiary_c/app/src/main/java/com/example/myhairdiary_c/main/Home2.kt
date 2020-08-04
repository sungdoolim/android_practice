package com.example.myhairdiary_c.main

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.MainActivity
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designerAdapter
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.album
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.style.Style_Search

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_home.botnav
import kotlinx.android.synthetic.main.activity_home2.*

class Home2 : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)


        var    Album= album(this)
        var db= fireDB(this)
        val pref=getSharedPreferences("session",0)
        val id:String=pref.getString("id","").toString()
        val profile=pref.getString("profile","").toString()
        User_Profile_Photo.setBackground(ShapeDrawable(OvalShape()));
        User_Profile_Photo.setClipToOutline(true)
        select_recommend_list(db.firestore) // recommend 리스트 출력

        select_trend_list(db.firestore) // recommend 리스트 출력

        select_recommend_designerList(db.firestore)



        if(id!=""&&profile!="")
            Glide.with(this).load(profile).into(User_Profile_Photo)

        println("url : ${profile}")
        editSearch.hint="ddfsefd"
        if(pref.getString("id","")!=""){
        User_greeting.text="환영합니다 "+pref.getString("id","")+" 님"
        }
        search_bt.setOnClickListener(){
            var key=editSearch.text.toString()
            var intent= Intent(this, Style_Search::class.java)
            intent.putExtra("style",key)
            startActivity(intent)
        }

        settings.setOnClickListener(){
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        botnav.setOnNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
//            R.id.bottom1->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()// fragment로 화면 전환 bottomnavi
//            R.id.bottom2->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
//            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
//            R.id.bottom4->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
//            R.id.bottom5->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()



            else ->""
        }
        return true;
    }



    public fun select_recommend_designerList(firestore: FirebaseFirestore) {// 지금은 recommend리스트랑 똑같음

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    println("designers  len = ${userDTO.size}")

                    // recommend_designer_list 는 id로 얻어온 recyclerview 임

                    recommend_designer_list.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    recommend_designer_list.setHasFixedSize(true)
                    recommend_designer_list.adapter=
                        designerAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }


    public fun select_trend_list(firestore: FirebaseFirestore) {// 지금은 recommend리스트랑 똑같음

        firestore?.collection("hair_photo").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                 //   println("userdto len = ${userDTO.size}")
                    trendlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    trendlist.setHasFixedSize(true)
                    trendlist.adapter=
                        recommend_trend_adapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }



    public fun select_recommend_list(firestore: FirebaseFirestore) {

        firestore?.collection("hair_photo").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                           // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                //    println("userdto len = ${userDTO.size}")
                    recommendlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    recommendlist.setHasFixedSize(true)
                    recommendlist.adapter=
                        recommend_trend_adapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}

