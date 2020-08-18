package com.example.myhairdiary_c.main.second.findpage

import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.detailedDesigner
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_find8page.*
import kotlinx.android.synthetic.main.bottom_navi.*
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL

class find8page : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find8page)

        botnav.getMenu().getItem(1).setChecked(true);
        val prefselected=getSharedPreferences("selected",0)
        val db= fireDB(this)
        val pref=getSharedPreferences("tab2",0)
        val edit=pref.edit()
        val gender=pref.getString("gender","").toString()
        val length=pref.getString("length","").toString()
        val kind=pref.getString("kind","").toString()
        val region=pref.getString("region","").toString()
        val region2=pref.getString("region2","").toString()
        val demand=pref.getString("demand","").toString()
        select_designer_list(db.firestore,gender,length,kind,region,demand)
        var url=prefselected.getString("profile","")
        var did=prefselected.getString("did","")
//intent.getStringExtra("profile")
        Glide.with(this).load(url).into(designer_photo)
        designer_photo.setBackground(ShapeDrawable(OvalShape()));
        designer_photo.setClipToOutline(true)
        designer_photo.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)
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
    public fun select_designer_list(firestore: FirebaseFirestore,gender:String,length:String,kind:String,region:String,demand:String) {


        firestore?.collection("hair_diary").whereEqualTo("perm",1)
            .whereEqualTo("region",region).whereEqualTo("major",kind).get()
            .addOnCompleteListener {

                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            if(length=="기타"){
                                userDTO.add(it1)
                            }else{
                                if(it1.major_length==length){
                                    userDTO.add(it1)
                                }else{

                                }
                            }

                        } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    findcomplete_designer.addItemDecoration(DividerItemDecoration(applicationContext,
                        HORIZONTAL))// 경계선 추가!!!!
                    findcomplete_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    findcomplete_designer.setHasFixedSize(true)
                    findcomplete_designer.adapter=
                        find7page.findcompleteAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }

}
