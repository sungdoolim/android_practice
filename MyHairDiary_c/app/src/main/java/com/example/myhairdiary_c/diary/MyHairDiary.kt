package com.example.myhairdiary_c.diary

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.marginTop
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.style.MyAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detailed_designer3.*
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.activity_my_hair_diary.*
import kotlinx.android.synthetic.main.diary_uppertab.*

class MyHairDiary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hair_diary)
        // 다이어리 탭입니다
        val db=fireDB(this)

        val pref=getSharedPreferences("session",0)  // session으로 preference에 저장한 것을 불러옵니다.
        val id:String=pref.getString("id","").toString()
        val profile=pref.getString("profile","").toString()
        val major=pref.getString("major","").toString()
        val major_length=pref.getString("major_length","").toString()
        val region=pref.getString("region","").toString()


        diary_uppertab.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onTabSelected(tab: TabLayout.Tab) {
                var i = tab.position

                if(i==0){
                    tab.text="냐옹"
                    select_my_register(db,id)// 내가 올린 게시물들을 봅니다
                }else{
                    select_designer_register(db,id) // 디자이너가 나에게 써준 기록을 봅니다
                }

            } override fun onTabUnselected(tab: TabLayout.Tab) { }
            override fun onTabReselected(tab: TabLayout.Tab) { }
        })
        var isvisible=false
        mhd_float.setOnClickListener(){
            isvisible=!isvisible
            Toast.makeText(this,"프로티이이ㅣ잉",Toast.LENGTH_SHORT).show()
            if(isvisible){
            mhd_float2.visibility= View.VISIBLE
            mhd_float3.visibility=View.VISIBLE
            }else{
                mhd_float2.visibility= View.INVISIBLE
                mhd_float3.visibility=View.INVISIBLE
            }
        }
        mhd_float2.setOnClickListener(){
//            var intent= Intent(this, Home2::class.java)
//            startActivity(intent)
        }
        mhd_float3.setOnClickListener(){
            var intent= Intent(this, MyBoard::class.java)
            startActivity(intent)
        }


        select_my_register(db,id)// 처음 화면은 내가 올린 게시물을 보는 것 입니다.


    }
    fun select_my_register(db: fireDB,id:String) {
        val firestore=db.firestore
        firestore?.collection("mydiary_photo").whereEqualTo("id",id).get()
            .addOnCompleteListener {

                if(it.isSuccessful){
                    println("mhd_photo 접근 성공")
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1)
                            // firebase로 부터 데이터를 가져오면 데이터들을 클래스에 담습니다. 그 클래스를 arraylist에 담습니다.
                        }
                    }
                    if(userDTO.size==0){
                        // 만약 아직 기록이 없다면...
                        mhd_const1.visibility=View.VISIBLE
                        Toast.makeText(this,"아직 나의 헤어 기록이 없어요!",Toast.LENGTH_SHORT).show()

                    }else{// 기록이 있으면 어댑터 적용
                        mhd_const1.visibility=View.INVISIBLE
                        mhd_iv1.top=110
                    var adapter = MyAdapter(
                        applicationContext,
                        R.layout.mhdgrid_adapter,  // GridView 항목의 레이아웃 row.xml
                        userDTO
                    )

                    mhd_gridview.adapter = adapter
                    mhd_gridview.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                        println("${item.name}")// 이렇게 데이터 받을수 있고...
                    }

                    }


                }else{
                    println("fail")
                }
            }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    fun select_designer_register(db: fireDB, id:String) {
        val firestore=db.firestore
        firestore?.collection("mydiary_designer").whereEqualTo("customid",id).get()
            .addOnCompleteListener {

                if(it.isSuccessful){
                    println("mhd_designer 접근 성공")
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1)
                            // firebase로 부터 데이터를 가져오면 데이터들을 클래스에 담습니다. 그 클래스를 arraylist에 담습니다.
                        }
                    }


                    if(userDTO.size==0) {


                        mhd_const1.visibility=View.VISIBLE
                        //mhd_iv1.setLeftTopRightBottom(0,10,0,100)


                    Toast.makeText(this,"아직 나의 헤어 기록이 없어요!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        mhd_const1.visibility=View.INVISIBLE
                        mhd_iv1.top=110
                        var adapter = MyAdapter(
                        applicationContext,
                        R.layout.mhdgrid_adapter,  // GridView 항목의 레이아웃 row.xml
                        userDTO
                    )

                    mhd_gridview.adapter = adapter
                    mhd_gridview.setOnItemClickListener { parent, view, position, id ->
                        var item= style_grid.adapter.getItem(position) as photourl
                        println("${item.name}")// 이렇게 데이터 받을수 있고...
                    }}


                }else{
                    println("fail")
                }
            }
    }
}