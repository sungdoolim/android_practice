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
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_review_list.*
import kotlinx.android.synthetic.main.bottom_navi.*

class reviewList : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_list)

        botnav.setOnNavigationItemSelectedListener(this)
        val db= fireDB(this)
        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        val prefselected=getSharedPreferences("selected",0)
        val selecedit=prefselected.edit()
        val did=prefselected.getString("did","").toString()
        var index=prefselected.getInt("index", -1)
        val id=pref.getString("id","").toString()

        select_designer_list(db.firestore,did) // 모든 디자이너 출력입니다.


        review_write.setOnClickListener(){
            var intent= Intent(this, designer_review::class.java)
            startActivity(intent)
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1->{
                var intent=Intent(this, Home2::class.java)
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
    public fun select_designer_list(firestore: FirebaseFirestore,did:String) {
        // 로직은 Home2의 select_recommend_designerList와 같습니다.
        firestore?.collection("hair_review").whereEqualTo("id",did).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<photourl>()
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 ->
                            userDTO.add(it1) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
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
                        photorvAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}