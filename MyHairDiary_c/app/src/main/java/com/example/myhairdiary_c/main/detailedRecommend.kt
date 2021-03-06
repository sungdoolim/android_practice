package com.example.myhairdiary_c.main

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designerAdapter
import com.example.myhairdiary_c.designers.designer_list
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_recommend.*
import kotlinx.android.synthetic.main.activity_find8page.*
import kotlinx.android.synthetic.main.bottom_navi.*

class detailedRecommend : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_recommend)

        val prefrecommend=getSharedPreferences("recommended",0)
        styledesc.text=prefrecommend.getString("memo","")
        val pref=getSharedPreferences("session",0)
        val edit=prefrecommend.edit()

        val id=pref.getString("id","").toString()
        var did=prefrecommend.getString("did","")
        var url=prefrecommend.getString("url","").toString()
        val style=prefrecommend.getString("style","")
        // 추천받은 스타일의 정보를 가져옵니다.

        Glide.with(this).load(url).into(selected_style_img)
        var db= fireDB(this)
        select_wecando(db.firestore,style!!)// 이 스타일을 할 수 있는 디자이너를 띄웁니다
        select_another_style(db.firestore,did!!)// 또다른 추천 스타일을 띄웁니다.
        isScrab(db.firestore,id,url)// 지금 스타일의 스크랩 여부에 따른 별 표를 바꿉니다
        isscrab.setOnClickListener(){
            val like=prefrecommend.getInt("like",0)
            addmystyle(db,id,did,url,like,edit) // 좋아요 수를 변동/   db 삭제를 합니다.
        }

        overview_recom.setOnClickListener(){
            var intent= Intent(this, designer_list::class.java)
            startActivity(intent)
        }
        botnav.setOnNavigationItemSelectedListener(this)
    }
    fun addmystyle(db:fireDB, id: String, did: String, url: String, like: Int, selecedit: SharedPreferences.Editor,){
        val firestore=db.firestore
        var userDTO=
            photourl(url,did,-1,"","","","","",id)
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_mystyle").whereEqualTo("customid",id)
            .whereEqualTo("id",did).whereEqualTo("url",url).get().addOnCompleteListener{
                if( it.result!!.documents.size!=0){
                    //값이 이미 존재하는지의 여부에 따라 삭제 삽입이 결정됩니다.

                 firestore?.collection("hair_mystyle").document(it.result!!.documents.get(0).id).delete()
                        .addOnCompleteListener {
                           isscrab.setImageResource(R.drawable.star_icon)
                            db.updateData_oneInt_url("hair_photo","like",like-1,url)
                            selecedit.putInt("like",like-1)
                            selecedit.apply()
                        }
                }else{
                    firestore?.collection("hair_mystyle")?.document()?.set(userDTO)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                isscrab.setImageResource(R.drawable.fullstart_icon)
                                db.updateData_oneInt_url("hair_photo","like",like+1,url)
                                selecedit.putInt("like",like+1)
                                selecedit.apply()

                            }
                        }
                }
            }
    }
   fun isScrab(firestore: FirebaseFirestore, id: String,url:String) {

       firestore?.collection("hair_mystyle").whereEqualTo("customid",id).get()
           .addOnCompleteListener {
               if(it.isSuccessful){
                   var userDTO=ArrayList<photourl>()
                    // 스크랩 여부를 판단합니다.

                   for(dc in it.result!!.documents){
                       dc.toObject(photourl::class.java)?.let { it1 ->
                       if(it1.url==url){
                       isscrab.setImageResource(R.drawable.fullstart_icon)
                       }
                       } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                   }

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
//            R.id.bottom5->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            else ->""
        }
        return true;
    }
    fun select_wecando(firestore:FirebaseFirestore,style:String){
        // 이 스타일이 가능한 또다른 디자이너를 찾습니다.
        firestore?.collection("hair_diary").whereEqualTo("perm",1).whereEqualTo("major",style).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    wecando_rv.addItemDecoration(
                        DividerItemDecoration(applicationContext,
                            DividerItemDecoration.HORIZONTAL
                        )
                    )// 경계선 추가!!!!
                    wecando_rv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    wecando_rv.setHasFixedSize(true)
                    wecando_rv.adapter=
                        designerAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
    fun select_another_style(firestore:FirebaseFirestore,did:String){
        // 이 디자이너의 또다른 스타일을 찾습니다.
        firestore?.collection("hair_photo").whereEqualTo("id",did).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    //    println("userdto len = ${userDTO.size}")
                    another_rv.addItemDecoration(
                        DividerItemDecoration(applicationContext,
                            DividerItemDecoration.HORIZONTAL
                        )
                    )// 경계선 추가!!!!
                    another_rv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    another_rv.setHasFixedSize(true)
                    another_rv.adapter=
                        recommend_trend_adapter(
                            this,
                            userDTO,3
                        )
                }else{
                    println("fail")
                }
            }
    }


}

