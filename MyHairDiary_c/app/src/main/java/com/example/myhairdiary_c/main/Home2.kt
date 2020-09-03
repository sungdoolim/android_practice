package com.example.myhairdiary_c.main

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.MainActivity
import com.example.myhairdiary_c.mypage.Mypage
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.Setting
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designerAdapter
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.album
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.logo_home
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.style.Style_Search
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_recommend.*
import kotlinx.android.synthetic.main.activity_home2.*
import kotlinx.android.synthetic.main.bottom_navi.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class Home2 : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_home2)
        // 실제 앱 시작 첫 화면입니다.


        var db= fireDB(this)  // 클래스로 firebase관련 목록을 따로 설정하여 사용하고자 했으나 많은 활용을 하지 못했습니다.

        val pref=getSharedPreferences("session",0)  // 로그인시 session으로 preference에 저장한 것을 불러옵니다.
        // 이것으로 session유지를 할 생각입니다.
        val id:String=pref.getString("id","").toString()
        val profile=pref.getString("profile","").toString()
        val major=pref.getString("major","").toString()
        val major_length=pref.getString("major_length","").toString()
        val region=pref.getString("region","").toString()
        User_Profile_Photo.setBackground(ShapeDrawable(OvalShape()));
        User_Profile_Photo.setClipToOutline(true)
        // 로그인한 유저의 프로필 사진 배경은 흑색 원형입니다.

        select_recommend_list(db.firestore,major) // recommend 리스트 출력
        select_trend_list(db.firestore) // recommend 리스트 출력
        select_recommend_designerList(db.firestore) // 추천 이자이너 출력



        if(id!="") {// 로그인을 했다면 환영 메시지를 출력하며
            User_greeting.text = "환영합니다 " + id + " 님"
            if (id != "" && profile != "") {// 프로필 사진이 있다면 사진을 로드 합니다.  없다면 안드로이드 모양일 들어갈 것 입니다.
                Glide.with(this).load(profile).into(User_Profile_Photo) }else{
                User_Profile_Photo.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }else{
            User_greeting.text = "환영합니다 로그인을 해 주세요"
        }
        // 유의 사항으로 아직 로그인/ 회원가입이 구현되지 않았습니다.  때문에 테스트 용도로 간단히 구현해 사용했습니다(MainActivity)
        // 네이버 연동을 목적으로 하는 것으로 알고 있습니다.


        editSearch.setOnClickListener(){
            // 이부분은 검색창을 눌렀을때 유저의 최근 검색어를 출력할 예정 이었습니다.

        }
        search_bt.setOnClickListener(){
            // 검색을 진행 합니다. 검색어를 가지고 intent로 값을 넘기게 됩니다.
            var intent= Intent(this, Style_Search::class.java)
            intent.putExtra("style",editSearch.text.toString())
            startActivity(intent)
        }
        settings.setOnClickListener(){
            // 오른쪽 맨 위 설정 버튼입니다. 설정으로 이동합니다.

            var intent= Intent(this, Setting::class.java)// 이게 공지나...그런 설정들

            startActivity(intent)
        }
        settings.setOnLongClickListener(){
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
             true
        }


        var ybefore:Int=0
        var ynow:Int=0
// 위 두 변수를 스크롤에 이용되는 변수들 입니다.
        scrollView2.getViewTreeObserver()
            .addOnScrollChangedListener(OnScrollChangedListener {
                // 스크롤을 내리고, 올릴때의 값들을 얻어 스크롤을 내릴때는 bottomnavigation이 사라지며
                // 스크롤을 올릴때는 bottom navigation이 나타납니다.
                // 생각보다 예민하게 작동되어 수정이 필요 할 수 있습니다.
             ybefore=ynow
                ynow=scrollView2.scrollY
                if(ynow-ybefore>0){
                    botnav.visibility= View.INVISIBLE
//                    home2_float.visibility=View.INVISIBLE
                }else{
                    botnav.visibility= View.VISIBLE
//                    home2_float.visibility=View.VISIBLE
                }
                println(" y :  ${ynow}")
            })
        botnav.setOnNavigationItemSelectedListener(this)

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
// bottom navigation의 버튼 이벤트 입니다.
        when(item.itemId){
            R.id.bottom1-> {
                var intent= Intent(this, this::class.java)
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
    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        finish()
    }
     fun select_recommend_designerList(firestore: FirebaseFirestore) {
         // 추천 디자이너를 출력합니다.
        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                // google firestore에서 hair_diary라는 collection을 만들고 perm이 1인 row를 전부 가져오는 것 입니다.
                // perm이 0 이면 고객 1이면 디자이너 입니다.
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            userDTO.add(it1)
                            // firebase로 부터 데이터를 가져오면 데이터들을 클래스에 담습니다. 그 클래스를 arraylist에 담습니다.
                            }
                    }
                    // recommend_designer_list 는 id로 얻어온 recyclerview 입니다.
                    recommend_designer_list.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL)
                    )// 경계선을 추가합니다
                    recommend_designer_list.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false) // 옆으로 스크롤 가능합니다.
                    recommend_designer_list.setHasFixedSize(true)
                    recommend_designer_list.adapter=
                        designerAdapter(this,userDTO)// 만들어둔 designerAdapter를 연결합니다. 이때 arraylist를 전달 합니다.
                }else{
                    println("fail")
                }
            }
    }


    public fun select_trend_list(firestore: FirebaseFirestore) {
        // 추천 트렌드를 출력합니다
        // 로직인 위 select_recommend_designerlist와 똑같습니다.
        // 때문에 하나의 메서드로 합칠까 고민 했지만 실패하여 따로 작성했습니다.

        firestore?.collection("hair_photo").whereEqualTo("pcount",0).get()
            .addOnCompleteListener {// 나중에은 where("perm",1)도 해줘야 할듯  -> 지금은 default값으로 0 만 들어가고 있음 // 또는 아예 hair_trend라는 table 만들기
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()

                    userDTO.add(photourl("","",0,"","오늘의 추천 트렌드를 만나보세요! ->","","","","",
                        0,"","","","","",0))
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1) }
                    }
                    println("userdtop len : ${userDTO.size}")
                    trendlist.addItemDecoration(
                        DividerItemDecoration(applicationContext,DividerItemDecoration.HORIZONTAL)
                    )
                    trendlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    trendlist.setHasFixedSize(true)
                    trendlist.adapter=
                        recommend_trend_adapter(this, userDTO,1)
                }else{
                    println("fail")
                }
            }
    }



    public fun select_recommend_list(firestore: FirebaseFirestore,major:String) {
        // 추천 스타일 출력합니다
        // 로직인 위 select_recommend_designerlist와 똑같습니다.
        // 때문에 하나의 메서드로 합칠까 고민 했지만 실패하여 따로 작성했습니다.
        firestore?.collection("hair_photo").whereEqualTo("style",major).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()

                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                        userDTO.add(it1) }
                    }
                    recommendlist.addItemDecoration(
                        DividerItemDecoration(applicationContext, DividerItemDecoration.HORIZONTAL)
                    )
                    recommendlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    recommendlist.setHasFixedSize(true)
                    recommendlist.adapter=
                        recommend_trend_adapter(this,userDTO,0 )
                }else{
                    println("fail")
                }
            }
    }
}


