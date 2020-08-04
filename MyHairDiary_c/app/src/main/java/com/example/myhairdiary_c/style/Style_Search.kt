package com.example.myhairdiary_c.style

import android.content.Context
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_style__search.*

class Style_Search : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_style__search)
        val pref=getSharedPreferences("session",0)
        var style=intent.getStringExtra("style").toString()
        selectList(style, "", "")
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
    internal class MyAdapter(
        context: Context,
        layout: Int,
        userDTO:ArrayList<photourl>
    ) :        BaseAdapter() {
        var context: Context
        var layout: Int
        var userDTO:ArrayList<photourl>
        var inf: LayoutInflater
        override fun getCount(): Int {
            return userDTO.size
        }
        override fun getItem(position: Int): Any {
            return userDTO[position]
        }
        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View? {
            var convertView = convertView
            if (convertView == null) convertView = inf.inflate(layout, null)
//            val iv =
//                convertView!!.findViewById<View>(R.id.imV) as ImageView
            //   iv.setImageResource(img[position])
            val iv=convertView!!.findViewById<ImageView>(R.id.search_imageurl)
            val tv=convertView!!.findViewById<TextView>(R.id.search_desc)
            tv.text=userDTO[position].name
            Glide.with(context).load(userDTO[position].url).into(iv)
            // iv.setImageResource(imgurl[position])
            return convertView
        }
        init {
            this.context = context
            this.layout = layout
            this.userDTO=userDTO
            inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }
// 밑에 노가다 selectlist있음





























































    public fun selectList(style: String, len: String, gender: String) {

        //하.... 나중에 개선 합시다....
        var firestore=FirebaseFirestore.getInstance()
        if(style==""&&len==""&&gender==""){
        }
        else if(style==""&&len!=""&&gender==""){
            firestore?.collection("hair_photo").whereEqualTo("len",len).get()
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
        else if(style==""&&len==""&&gender!=""){
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
        else if(style==""&&len!=""&&gender!=""){
            var firestore=FirebaseFirestore.getInstance()
            firestore?.collection("hair_photo").whereEqualTo("len",len).whereEqualTo("gender",gender).get()
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
        else if(style!=""&&len==""&&gender==""){
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
        else if(style!=""&&len!=""&&gender==""){
            firestore?.collection("hair_photo").whereEqualTo("style",style).whereEqualTo("len",len).get()
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
        else if(style!=""&&len==""&&gender!=""){
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
            firestore?.collection("hair_photo").whereEqualTo("style",style).whereEqualTo("len",len).whereEqualTo("gender",gender).get()
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
