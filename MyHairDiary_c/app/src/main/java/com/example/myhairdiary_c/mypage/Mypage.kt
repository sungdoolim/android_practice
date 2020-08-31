package com.example.myhairdiary_c.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.Setting
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.activity_mypage.User_Profile_Photo
import kotlinx.android.synthetic.main.bottom_navi.*


class Mypage : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        botnav.getMenu().getItem(3).setChecked(true) // 바텀네비 고정입니다. 원래 이런식으로 하는건 아니죠....
        botnav.setOnNavigationItemSelectedListener(this)
        val pref=getSharedPreferences("session",0)
        val id=pref.getString("id","").toString()
        val mail=pref.getString("mail","").toString()

        myname.text=id+" 님!"
        myemail.text="오늘도 기분 좋은 하루 되세요~"
        Glide.with(this).load(pref.getString("profile","")).into(User_Profile_Photo)

        val db= fireDB(this)
        val firestore=db.firestore
        //   myfav_designer /   myfav_style
        select_mystyle(firestore,id)// 내가 스크랩한 스타일 사진을 로드
        select_mydesigner(firestore,id)// 내가 스크랩한 디자이너를 로드
        settingsbt.setOnClickListener(){
            var intent= Intent(this, Setting::class.java)// 이게 공지나...그런 설정들
            startActivity(intent)
        }
        goback.setOnClickListener(){
            this.onBackPressed()
        }
        gotomypage2.setOnClickListener(){
            var intent= Intent(this, Mypage2::class.java)
            startActivity(intent)
        }
        mydesigners.setOnClickListener(){
            var intent= Intent(this, glide_mydesigners::class.java)
            startActivity(intent)
        }
        mystyles.setOnClickListener(){
            var intent= Intent(this, glide_mystyles::class.java)
            startActivity(intent)
        }
    }

    public fun select_mydesigner(firestore: FirebaseFirestore,id: String ) {
        firestore?.collection("hair_mydesigner").whereEqualTo("customid",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->

                            userDTO.add(it1) } }
                    println("designers  len = ${userDTO.size}")

                    myfav_designer.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL)
                    )
                    myfav_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    myfav_designer.setHasFixedSize(true)
                    myfav_designer.adapter=heart_mydesignerAdapter(this,userDTO)
                    // 어뎁터를 연결합니다.
                }else{
                    println("fail")
                }
            }
    }

    fun select_mystyle(firestore: FirebaseFirestore,id: String) {
        firestore?.collection("hair_mystyle").whereEqualTo("customid",id).get()
            .addOnCompleteListener {
                // 쿼리에 따른 사진들을 얻어와서 출력합니다
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1) }
                    }
                    println("designers  len = ${userDTO.size}")
                    myfav_style.addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))

                    myfav_style.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    myfav_style.setHasFixedSize(true)
                    myfav_style.adapter=heart_mystyleAdapter(this,userDTO)
                }else{
                    println("fail")
                }
            }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bottom1-> {
                var intent= Intent(this, Home2::class.java)
                startActivity(intent)
            }
            R.id.bottom2->
            {
                var intent= Intent(this, second_home::class.java)
                startActivity(intent)
            }
            R.id.bottom3->{
                var intent= Intent(this, MyHairDiary::class.java)
                startActivity(intent)
            }
            R.id.bottom4->{
                var intent= Intent(this, Mypage::class.java)
                startActivity(intent)
            }
            else ->""
        }
        return true
    }
}
