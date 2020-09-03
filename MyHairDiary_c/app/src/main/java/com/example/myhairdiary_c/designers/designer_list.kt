package com.example.myhairdiary_c.designers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_designer_list.*
import kotlinx.android.synthetic.main.bottom_navi.*

class designer_list : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designer_list)
        val db= fireDB(this)
        select_designer_list(db.firestore) // 모든 디자이너 출력입니다.
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
            else ->""
        }
        return true;
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    public fun select_designer_list(firestore:FirebaseFirestore) {
        // 로직은 main/Home2의 select_recommend_designerList와 같습니다.
        // main/Home2의 주석을 참고하십시오

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    designerlist.addItemDecoration(
                        DividerItemDecoration(applicationContext,
                            DividerItemDecoration.VERTICAL
                        )
                    )// 경계선 추가!!!!
                    designerlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    designerlist.setHasFixedSize(true)
                    designerlist.adapter=
                        designerAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}
