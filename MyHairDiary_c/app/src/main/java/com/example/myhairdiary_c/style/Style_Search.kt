package com.example.myhairdiary_c.style

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_style__search.*
import kotlinx.android.synthetic.main.bottom_navi.*

class Style_Search : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style__search)

        val pref=getSharedPreferences("session",0)
        var style=intent.getStringExtra("style").toString()
        selectList(style, "길이", "성별")
        var len:String=""
        var gender:String=""

        spin_style.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                style=""

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("position : ${position}+ id : ${id}")
                style= spin_style.selectedItem.toString() // 스피너는 요렇게 가져오는 구나
                println("style = ${style}")
            }
        }
        spin_len.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {                len=""

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("position : ${position}+ id : ${id}")
                len= spin_len.selectedItem.toString()
                println("len = ${len}")
                //len=position
            }
        }
        spin_gender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {     gender=""
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                println("position : ${position}+ id : ${id}")
                gender= spin_gender.selectedItem.toString()
                println("gender = ${gender}")
                //gender=position
            }
        }
        search_commit.setOnClickListener(){
            println("${style} and ${len} and ${gender}")
            selectList(style,len,gender)
        }

        botnav.getMenu().getItem(1).setChecked(true);
        botnav.setOnNavigationItemSelectedListener(this)

    }
    override fun onBackPressed() {
        super.onBackPressed()
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

// 밑에 노가다 selectlist있음





























































    public fun selectList(style: String, len: String, gender: String) {

        //하.... 나중에 개선 합시다....
        var firestore=FirebaseFirestore.getInstance()
        if(style=="스타일"&&(len=="길이"||len=="기타")&&(gender=="성별")){
            firestore?.collection("hair_photo").get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
        else if(style=="스타일"&&(len!="길이"&&len!="기타")&&(gender==""||gender=="성별")){
            firestore?.collection("hair_photo").whereEqualTo("length",len).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
        //style=="스타일을 선택하세요"&&(len=="길이를 선택하세요"||len=="기타")&&(gender=="성별을 선택하세요")
        else if(style=="스타일"&&len=="길이"&&gender!="성별"){
            firestore?.collection("hair_photo").whereEqualTo("gender",gender).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
         else if(style=="스타일"&&(len!="길이"&&len!="기타")&&gender!="성별"){
            var firestore=FirebaseFirestore.getInstance()
            firestore?.collection("hair_photo").whereEqualTo("length",len).whereEqualTo("gender",gender).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
        else if(style!="스타일"&&(len=="길이"||len=="기타")&&gender=="성별"){
            firestore?.collection("hair_photo").whereEqualTo("style",style).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
        else if(style!="스타일"&&(len!="길이"&&len!="기타")&&gender=="성별"){
            firestore?.collection("hair_photo").whereEqualTo("style",style).whereEqualTo("length",len).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
        else if(style!="스타일"&&(len=="길이"||len=="기타")&&gender!="성별"){
            firestore?.collection("hair_photo").whereEqualTo("style",style).whereEqualTo("gender",gender).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }
        else {
            firestore?.collection("hair_photo").whereEqualTo("style",style).whereEqualTo("length",len).whereEqualTo("gender",gender).get()
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
                        search_grid.adapter = adapter
                        search_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= search_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }
        }

    }

}
