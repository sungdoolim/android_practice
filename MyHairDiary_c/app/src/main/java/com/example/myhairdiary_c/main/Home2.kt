package com.example.myhairdiary_c.main

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.mypage.Mypage
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.Setting
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designerAdapter
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.album
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.style.Style_Search
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.botnav
import kotlinx.android.synthetic.main.activity_home2.*


class Home2 : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)
//        val handler = Handler()
//        handler.postDelayed({ finish() }, 2000)


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

        if(pref.getString("id","")!=""){
        User_greeting.text="환영합니다 "+pref.getString("id","")+" 님"
        }


        editSearch.setOnClickListener(){

//            constraintLayout2.addView()
//            var item=arrayOf("1","2","3")
//            search_lv.adapter= ArrayAdapter <Any>(this, android.R.layout.simple_list_item_1, item)
//            search_lv.setOnItemClickListener { parent, view, position, id ->
//                println(position)
//                println(search_lv.getItemAtPosition(position))
//            }




            Toast.makeText(this,"냐옹",Toast.LENGTH_SHORT).show()
        }
        search_bt.setOnClickListener(){
            var key=editSearch.text.toString()
            var intent= Intent(this, Style_Search::class.java)
            intent.putExtra("style",key)
            startActivity(intent)
        }


        settings.setOnClickListener(){
            var intent= Intent(this, Setting::class.java)
            startActivity(intent)
        }
        var ybefore:Int=0
        var ynow:Int=0



        scrollView2.getViewTreeObserver()
            .addOnScrollChangedListener(OnScrollChangedListener {
//                var x= scrollView2.scrollX
//                var y= scrollView2.scrollY
             ybefore=ynow
                ynow=scrollView2.scrollY

                if(ynow-ybefore>0){
                    botnav.visibility= View.INVISIBLE
                }else{
                    botnav.visibility= View.VISIBLE
                }
                println(" y :  ${ynow}")
            })
        botnav.setOnNavigationItemSelectedListener(this)
       // botnav.visibility= View.INVISIBLE

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.bottom2->
            {
                var intent= Intent(this, second_home::class.java)
                startActivity(intent)
            }
//            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom4->{
                var intent= Intent(this, Mypage::class.java)
                startActivity(intent)


            }
//            R.id.bottom5->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            else ->""
        }
        return true;
    }


    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        finish()
    }
     fun select_recommend_designerList(firestore: FirebaseFirestore) {// 지금은 recommend리스트랑 똑같음 // 얘가 맨 마지막애인가바

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
                            userDTO,1
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
                            userDTO,0
                        )
                }else{
                    println("fail")
                }
            }
    }
}


