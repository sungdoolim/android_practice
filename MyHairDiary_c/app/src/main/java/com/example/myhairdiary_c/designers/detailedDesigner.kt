package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.example.myhairdiary_c.style.MyAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_designer.*
import kotlinx.android.synthetic.main.bottom_navi.*
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer)
        val db=fireDB(this)
     //   bt1.setBackgroundColor(R.color.colorAccent)

        val prefselected=getSharedPreferences("selected",0)
        val pref=getSharedPreferences("session",0)
        bt1.setImageResource(R.drawable.num1_icon)

        bt2.setOnClickListener(){
            var intent= Intent(this, detailedDesigner2::class.java)
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




        var id=pref.getString("id","").toString()

        var url=prefselected.getString("profile","").toString()
        var did=prefselected.getString("did","").toString()
        val age=prefselected.getInt("age",0).toString()
        val major=prefselected.getString("major","").toString()
        val reviewcount=prefselected.getInt("reviewcount",0).toString()
        detailed_designer_name.text="이름 : "+pref.getString("name","")+"\nID : "+did
        designer_greeting.text="나이 : "+age+"\n경력 : "+"\n전문 분야 : "+major+"\n리뷰 수 : "+reviewcount
        memo.text=prefselected.getString("memo","").toString()
        var mymajor=pref.getString("major","").toString()
        recommended_url(db.firestore,did,mymajor)



//intent.getStringExtra("profile")
        Glide.with(this).load(url).into(designer_photo)
        designer_photo.setBackground(ShapeDrawable(OvalShape()));
        designer_photo.setClipToOutline(true)
        selectList(db.firestore,did!!)

        review_imgbt.setOnClickListener(){
            Toast.makeText(this,"toast!",Toast.LENGTH_SHORT).show()
        }


        botnav.setOnNavigationItemSelectedListener(this)
        isScrab(db.firestore,id,did)
        isscrab.setOnClickListener(){
            addmystyle(db.firestore,id,did,url!!)
        }
    }
    fun recommended_url(firestore:FirebaseFirestore,did:String,mymajor:String){
        firestore?.collection("hair_photo").whereEqualTo("id",did).whereEqualTo("style",mymajor).get()
            .addOnCompleteListener {
                var userDTO=ArrayList<photourl>()
                if(it.isSuccessful){
                    if(it.result!!.documents.size!=0){
                        //Toast.makeText(this,"${it.result!!.documents[0]["url"]}",Toast.LENGTH_SHORT).show()
                        Glide.with(this).load(it.result!!.documents[0]["url"]).into(clicyourstyle)
                        photomemo.text=it.result!!.documents[0]["memo"].toString()
                    }
                }else{
                    println("fail")
                }
            }
    }
    fun addmystyle(firestore: FirebaseFirestore,id: String,did: String,url:String=""){

        var userDTO=
            designer(did,0,"",0,"",0,1,0,0,
                "","","","",0,"","",url,
                "","","","","","",id)
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_mydesigner").whereEqualTo("customid",id)
            .whereEqualTo("id",did).get().addOnCompleteListener{
                if( it.result!!.documents.size!=0){
                    firestore?.collection("hair_mydesigner").document(it.result!!.documents.get(0).id).delete()
                        .addOnCompleteListener {
                                isscrab.setImageResource(R.drawable.star_icon)
                        }
                }else{
                    firestore?.collection("hair_mydesigner")?.document()?.set(userDTO)
                        .addOnCompleteListener {
                            if(it.isSuccessful)
                                isscrab.setImageResource(R.drawable.fullstart_icon)
                        }
                }
            }
    }
    fun isScrab(firestore:FirebaseFirestore,id:String,did:String){
        firestore?.collection("hair_mydesigner").whereEqualTo("customid",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            if(it1.id==did){
                                isscrab.setImageResource(R.drawable.fullstart_icon)
                            }
                        }
                    }
                }else{
                    Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.bottom2->
            {
                var intent= Intent(this, second_home::class.java)
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
       // super.onBackPressed()

        var intent= Intent(this, Home2::class.java)
        startActivity(intent)
    }
    fun selectList(firestore: FirebaseFirestore,did:String){
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
                    detailed_grid.adapter = adapter
                    detailed_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= detailed_grid.adapter.getItem(position) as photourl
                        println("${item.name}")// 이렇게 데이터 받을수 있고...
                    }
                    print("select list clear")
                }else{
                    println("fail")
                }
            }
    }
}
