package com.example.myhairdiary_c.diary

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.widget.AbsListView
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.example.myhairdiary_c.style.MyAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_detailed_designer3.*
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.activity_my_hair_diary.*
import kotlinx.android.synthetic.main.activity_my_hair_diary.view.*
import kotlinx.android.synthetic.main.bottom_navi.*
import kotlinx.android.synthetic.main.diary_uppertab.*

class MyHairDiary : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hair_diary)

        botnav.getMenu().getItem(2).setChecked(true) // 바텀네비 고정입니다. 원래 이런식으로 하는건 아니죠....
        botnav.setOnNavigationItemSelectedListener(this)
        // 다이어리 탭입니다
        val db=fireDB(this)

        val pref=getSharedPreferences("session",0)  // session으로 preference에 저장한 것을 불러옵니다.
        val id:String=pref.getString("id","").toString()
        val profile=pref.getString("profile","").toString()
        val major=pref.getString("major","").toString()
        val major_length=pref.getString("major_length","").toString()
        val region=pref.getString("region","").toString()


        diary_uppertab.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {
                // 탭에 따라 메서드가 달라집니다.
                // 내가 올린 사진 / 디자이너가 올려준 사진들을 보게 됩니다.

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
            // 플로팅 버튼입니다.  float2,3버튼을 보이게 하며 현재 기능은 3버튼만 기능을 가집니다.
            isvisible=!isvisible
            Toast.makeText(this,"floating test",Toast.LENGTH_SHORT).show()
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
        mhd_iv1.setOnClickListener(){
            // 이걸로 그리드뷰의 넓이를 조절하려 했으나 실패했습니다...
            if(mhd_gridview.top==450){
                mhd_gridview.top=600
            }else{
                mhd_gridview.top=450
            }
        }




        select_my_register(db,id)// 처음 화면은 내가 올린 게시물을 보는 것 입니다.


    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
// bottom navigation의 버튼 이벤트 입니다.
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
                var intent= Intent(this, this::class.java)
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




    fun select_my_register(db: fireDB,id:String) {
        // 내가 올린 사진을 불러옵니다
        val firestore=db.firestore
        firestore?.collection("hair_photo").whereEqualTo("id",id).get()
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
                        mhd_gridview.visibility=View.INVISIBLE
                        isexist.visibility=View.VISIBLE

                     //   mhd_gridview.y= 100f// 이거로 변경해도 되나....

                        Toast.makeText(this,"아직 나의 헤어 기록이 없어요!",Toast.LENGTH_SHORT).show()
                        mhd_gridview.removeAllViewsInLayout()

                        mhd_gridview.refreshDrawableState()
                        var adapter = MyAdapter(
                            applicationContext,
                            R.layout.mhdgrid_adapter,  // GridView 항목의 레이아웃 row.xml
                            userDTO,-1
                        )

                        mhd_gridview.adapter = adapter
                        mhd_gridview.setOnItemClickListener { parent, view, position, id ->
                            var item= style_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }



                    }else{// 기록이 있으면 어댑터 적용

                        mhd_gridview.visibility=View.VISIBLE
                        isexist.visibility=View.INVISIBLE

                     //   mhd_gridview.y= 500f// 이거로 변경해도 되나....
  //                      mhd_iv1.layoutParams= ConstraintLayout.LayoutParams(50,50)

                    var adapter = MyAdapter(
                        applicationContext,
                        R.layout.mhdgrid_adapter,  // GridView 항목의 레이아웃 row.xml
                        userDTO,1
                    )

                    mhd_gridview.adapter = adapter
                    mhd_gridview.setOnItemClickListener { parent, view, position, id ->
                        var item= mhd_gridview.adapter.getItem(position) as photourl
                        println("${item.name}")// 이렇게 데이터 받을수 있고...
                    }

                    }


                }else{
                    println("fail")
                }
            }
    }
    fun select_designer_register(db: fireDB, id:String) {
        // 디자이너가 찍은 내 사진을 불러옵니다.
        val firestore=db.firestore
        firestore?.collection("hair_photo").whereEqualTo("customid",id).get()
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

                        mhd_gridview.visibility=View.INVISIBLE
                        isexist.visibility=View.VISIBLE

                        //mhd_iv1.setLeftTopRightBottom(0,10,0,100)
                        var adapter = MyAdapter(
                            applicationContext,
                            R.layout.mhdgrid_adapter,  // GridView 항목의 레이아웃 row.xml
                            userDTO,-1
                        )

                        mhd_gridview.adapter = adapter
                        mhd_gridview.setOnItemClickListener { parent, view, position, id ->
                            var item= style_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }

                    Toast.makeText(this,"아직 나의 헤어 기록이 없어요!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        mhd_gridview.visibility=View.VISIBLE
                        isexist.visibility=View.INVISIBLE

                        //     mhd_iv1.top=110
                        var adapter = MyAdapter(
                        applicationContext,
                        R.layout.mhdgrid_adapter,  // GridView 항목의 레이아웃 row.xml
                        userDTO,1
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

}