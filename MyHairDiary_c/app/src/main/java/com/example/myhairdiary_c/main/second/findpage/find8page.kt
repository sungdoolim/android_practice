package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.detailedDesigner
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_find8page.*
import kotlinx.android.synthetic.main.bottom_navi.*
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.main.second.second_home

class find8page : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
// find7page에서 디자이너를 선택했을때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find8page)

        botnav.getMenu().getItem(1).setChecked(true);
    botnav.setOnNavigationItemSelectedListener(this)
        val prefselected=getSharedPreferences("selected",0)
        val db= fireDB(this)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        val gender=pref.getString("gender","").toString()
        val length=pref.getString("length","").toString()
        val kind=pref.getString("kind","").toString()
        val region=pref.getString("region","").toString()
        val region2=pref.getString("region2","").toString()
        val demand=pref.getString("demand","").toString()
    // 선택한 디자이너의 모든 정보를 가져옵니다.
    if(region=="전 지역"||region=="선택 하기"){
        select_designer_listAll(db.firestore,gender,length,kind,region,region2,demand)
    }else if(region2=="전 지역"||region2=="선택 하기"){
        select_designer_listPartAll(db.firestore,gender,length,kind,region,region2,demand)

    }else {
        select_designer_list(db.firestore, gender, length, kind, region, region2, demand)
    }


        var url=prefselected.getString("profile","")
        var did=prefselected.getString("did","")

        Glide.with(this).load(url).into(designer_photo)
        designer_photo.setBackground(ShapeDrawable(OvalShape()));
        designer_photo.setClipToOutline(true)


        designer_photo.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)
        }



    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1-> {
                var intent= Intent(this, Home2::class.java)
                startActivity(intent)
            }

            R.id.bottom2->
            {                var intent= Intent(this, second_home::class.java)
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
        return true;
    }
    public fun select_designer_list(firestore: FirebaseFirestore,gender:String,length:String,kind:String,region:String,region2:String,demand:String) {
        firestore?.collection("hair_diary").whereEqualTo("perm",1)
            .whereEqualTo("region",region).whereEqualTo("major",kind).get()
            .addOnCompleteListener {

                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            if(length=="기타"){
                                userDTO.add(it1)
                            }else{
                                if(it1.major_length==length){
                                    userDTO.add(it1)
                                }else{
                                }
                            }
                        }
                    }
                    findcomplete_designer.addItemDecoration(DividerItemDecoration(applicationContext,
                        HORIZONTAL))// 경계선 추가!!!!
                    findcomplete_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    findcomplete_designer.setHasFixedSize(true)
                    findcomplete_designer.adapter=
                        find7page.findcompleteAdapter(this,   userDTO )
                }else{
                    println("fail")
                }
            }
    }

    fun select_designer_listAll(firestore: FirebaseFirestore,gender:String,length:String,kind:String,region:String,region2:String,demand:String){
        // 로직은 밑 두 메서드와 같습니다.
        // 유저가 선택한 조건에 맞게 리스트를 구성해 recyclerview로 넘깁니다.
        firestore?.collection("hair_diary").whereEqualTo("perm",1)
            .whereEqualTo("major",kind).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            if(length=="기타"){
                                userDTO.add(it1)
                            }else{
                                if(it1.major_length==length){
                                    userDTO.add(it1)
                                }else{
                                }
                            }
                        } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    findcomplete_designer.addItemDecoration(DividerItemDecoration(applicationContext,
                        HORIZONTAL))// 경계선 추가!!!!
                    findcomplete_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    findcomplete_designer.setHasFixedSize(true)
                    findcomplete_designer.adapter=
                        find7page.findcompleteAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
    fun select_designer_listPartAll(firestore: FirebaseFirestore,gender:String,length:String,kind:String,region:String,region2:String,demand:String){
        firestore?.collection("hair_diary").whereEqualTo("perm",1)
            .whereEqualTo("region",region).whereEqualTo("major",kind).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->

                            if(length=="기타"){
                                userDTO.add(it1)
                            }else{
                                if(it1.major_length==length){
                                    userDTO.add(it1)
                                }else{

                                }
                            }

                        }
                    }
                    findcomplete_designer.addItemDecoration(DividerItemDecoration(applicationContext,
                        HORIZONTAL))
                    findcomplete_designer.layoutManager=LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    findcomplete_designer.setHasFixedSize(true)
                    findcomplete_designer.adapter=
                        find7page.findcompleteAdapter(this, userDTO)
                }else{
                    println("fail")
                }
            }
    }

}
