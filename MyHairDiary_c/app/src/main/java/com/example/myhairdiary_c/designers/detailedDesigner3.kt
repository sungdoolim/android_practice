package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.example.myhairdiary_c.style.MyAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_designer3.*
import kotlinx.android.synthetic.main.bottom_navi.*
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner3 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    // 여기는 detailedDesigner2와 같은데 gridview를 사용합니다. 디자이너 분들의 의도가 맞는지 확인이 필요합니다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer3)
      //  bt3.setBackgroundColor(R.color.colorAccent)
        bt3.setImageResource(R.drawable.num3_icon)
        val prefselected=getSharedPreferences("selected",0)
        var did=prefselected.getString("did","")
        selectList(did!!)
        val db= fireDB(this)
        var style:String=""
        var len:String=""
        var gender:String=""
        spin_style.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                style=""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                style= spin_style.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
            }
        }
        spin_len.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                len=""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                len= spin_len.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
            }
        }
        spin_gender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                gender=""

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                gender= spin_gender.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
            }
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
        bt1.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)
        }
        bt2.setOnClickListener(){
            var intent= Intent(this, detailedDesigner2::class.java)
            startActivity(intent)
        }
        bt4.setOnClickListener(){
            var intent= Intent(this, detailedDesigner4::class.java)
            startActivity(intent)
        }
        botnav.setOnNavigationItemSelectedListener(this)
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
//            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom4->{
                var intent= Intent(this, Mypage::class.java)
                startActivity(intent)
            }
            else ->""
        }
        return true
    }

    public fun selectList(did: String) {

        var firestore= FirebaseFirestore.getInstance()

            firestore?.collection("hair_photo").whereEqualTo("id",did).get()
                .addOnCompleteListener {
                    var userDTO=ArrayList<photourl>()
                    if(it.isSuccessful){
                        for(dc in it.result!!.documents){
                            dc.toObject(photourl::class.java)?.let { it1 -> userDTO.add(it1) }
                        }
                        var adapter = MyAdapter(
                            applicationContext,
                            R.layout.search_grid_adapter,  // GridView 항목의 레이아웃 row.xml
                            userDTO
                        )

                        style_grid.adapter = adapter
                        style_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= style_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }



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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }


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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
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

                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
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
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO )
                    style_grid.adapter = adapter
                    style_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                    }
                }else{
                    println("fail")
                }
            }
    }

}
