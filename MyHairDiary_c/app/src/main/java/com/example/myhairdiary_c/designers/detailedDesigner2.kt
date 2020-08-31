package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_designer2.*
import kotlinx.android.synthetic.main.bottom_navi.*
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer2)
       // bt2.setBackgroundColor(R.color.colorAccent)
        bt2.setImageResource(R.drawable.num2_icon)
        val prefselected=getSharedPreferences("selected",0)
        var did=prefselected.getString("did","")
        val db= fireDB(this)
        select_hair(db.firestore,did!!) // recommend 리스트 출력

        var style:String=""
        var len:String=""
        var gender:String=""
        spin_style.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                style="스타일"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                style= spin_style.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
            }
        }
        spin_len.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                len="길이"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                len= spin_len.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
            }
        }
        spin_gender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                gender="성별"

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gender= spin_gender.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
            }
        }
        // 여기까지는 스피너의 값을 받아오는 것 입니다.



        bt1.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)

        }
        bt3.setOnClickListener(){
            var intent= Intent(this, detailedDesigner3::class.java)
            startActivity(intent)
        }
        bt4.setOnClickListener(){
            var intent= Intent(this, detailedDesigner4::class.java)
            startActivity(intent)
        }
        search_commit.setOnClickListener(){
            // 검색 버튼으로 스피너 값 조건에 맞게 출력합니다.
            if(style=="스타일"){
                if(len=="길이"||len=="기타"){
                    if(gender=="기타"||gender=="성별"){
                        select_hair(db.firestore,did!!)
                    }else{
                        select_hair_gender(db.firestore,did!!,gender)
                    }
                }else{
                    if(gender=="기타"||gender=="성별"){
                        select_hair_len(db.firestore,did!!,len)
                    }else{
                        select_hair_len_gender(db.firestore,did!!,len,gender)
                    }
                }
            }else{
                if(len=="길이"||len=="기타"){
                    if(gender==""){
                            select_hair_style(db.firestore,did!!,style)
                    }else{
                            select_hair_style_gender(db.firestore,did!!,style,gender)
                    }
                }else{
                    if(gender=="기타"||gender=="성별"){
                        select_hair_style_len(db.firestore,did!!,style,len)
                    }else{
                        select_hair_all(db.firestore,did!!,style,len,gender)
                    }
                }
            }
        }
        botnav.setOnNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1->{
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
//            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom4->{
                var intent= Intent(this, Mypage::class.java)
                startActivity(intent)
            }
            else ->""
        }
        return true;
    }


    public fun select_hair_all(firestore: FirebaseFirestore,did: String,style:String,len:String,gender:String) {
        // 이 밑의 7개 메서드는 전부 로직이 같습니다.
        // 다만 하나의 메서드로 만들기에 생각할 점이 많아져 따로 분리했습니다

        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("style",style).whereEqualTo("length",len).whereEqualTo("gender",gender).get()
            .addOnCompleteListener {
                // 선택한 스피너에 맞는 값(사진)만을 불러옵니다.
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->

                            userDTO.add(it1)
                        }
                    }
                    hair_photo_listRv.addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))
                    hair_photo_listRv.layoutManager=LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }
     fun select_hair_style_len(firestore: FirebaseFirestore,did: String,style:String,len:String) {
        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("style",style).whereEqualTo("length",len).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1)
                        }
                    }
                    hair_photo_listRv.addItemDecoration( DividerItemDecoration(applicationContext,  DividerItemDecoration.HORIZONTAL  ))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=  photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }

    public fun select_hair_style_gender(firestore: FirebaseFirestore,did: String,style:String,gender:String) {
        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("style",style).whereEqualTo("gender",gender).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1)
                        }
                    }
                        hair_photo_listRv.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL ))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }

     fun select_hair_style(firestore: FirebaseFirestore,did: String,style:String) {
        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("style",style).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                         userDTO.add(it1)
                        }
                    }
                    hair_photo_listRv.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }

    fun select_hair_len_gender(firestore: FirebaseFirestore,did: String,len:String,gender:String) {// 지금은 recommend리스트랑 똑같음
        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("length",len).whereEqualTo("gender",gender).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1)
                        }
                    }

                    hair_photo_listRv.addItemDecoration(DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=
                        photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }




    public fun select_hair_gender(firestore: FirebaseFirestore,did: String,gender:String) {
        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("gender",gender).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                        userDTO.add(it1)
                        }
                    }
                 hair_photo_listRv.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter= photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }

    public fun select_hair_len(firestore: FirebaseFirestore,did: String,len:String) {
        firestore?.collection("hair_photo").whereEqualTo("id",did)
            .whereEqualTo("length",len).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                           userDTO.add(it1)
                        }
                    }
                    hair_photo_listRv.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }

    public fun select_hair(firestore: FirebaseFirestore,did: String) {
        firestore?.collection("hair_photo").whereEqualTo("id",did).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1)
                        }
                    }
                    hair_photo_listRv.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL))
                    hair_photo_listRv.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    hair_photo_listRv.setHasFixedSize(true)
                    hair_photo_listRv.adapter=photorvAdapter( this,  userDTO)
                }else{
                    println("fail")
                }
            }
    }
}
