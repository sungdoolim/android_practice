package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
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

    @SuppressLint("ResourceAsColor", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer)
        val db=fireDB(this)
     //   bt1.setBackgroundColor(R.color.colorAccent)

        val prefselected=getSharedPreferences("selected",0)
        val selecedit=prefselected.edit()
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
        //bt1~4은 사실 fragment로 구성했어야 한다 생각합니다.
        //그러나 fragment로 화면 이동시에 xml의 조작과 데이터 조작에 미숙해 intent로 구성하였습니다.

        var id=pref.getString("id","").toString()
        var url=prefselected.getString("profile","").toString()
        var did=prefselected.getString("did","").toString()
        val age=prefselected.getInt("age",0).toString()

        val major=prefselected.getString("major","").toString()
        val reviewcount=prefselected.getInt("reviewcount",0).toString()
        // 선택된 디자이너의 정보를 저장했던 preference에서 꺼내옵니다.

        detailed_designer_name.text="이름 : "+pref.getString("name","")+"\nID : "+did
        designer_greeting.text= "나이 : $age\n경력 : \n전문 분야 : $major\n리뷰 수 : $reviewcount"
        memo.text=prefselected.getString("memo","").toString()
        var mymajor=pref.getString("major","").toString()

        recommended_url(db.firestore,did,mymajor)// 유저의 저장된 데이터 기반 (mymajor)을 가지고 같은 값을 가진 디자이너를 띄웁니다.
        Glide.with(this).load(url).into(designer_photo)// 선택된 디자이너의 프로필 사진을 띄웁니다
        designer_photo.setBackground(ShapeDrawable(OvalShape()));
        designer_photo.setClipToOutline(true)

        selectList(db.firestore,did!!) //선택된 디자이너가 올린 사진들을 출력합니다.

        review_imgbt.setOnClickListener(){
            Toast.makeText(this,"toast!",Toast.LENGTH_SHORT).show()
            // 리뷰 보기 버튼 같은데 아직 레이아웃을 받지 못해 안했습니다.
            // 리뷰 다는 기능 역시 구현되어 있지 않습니다.
        }


        botnav.setOnNavigationItemSelectedListener(this)
        isScrab(db.firestore,id,did) // 스크랩이 이미 되어있다면 까만 별 / 아니면 투명별
        isscrab.setOnClickListener(){
            val like=prefselected.getInt("like",0)
            addmystyle(db,id,did,url!!,like,selecedit)// 별아이콘으로 즐겨찾기, 좋아요 기능을 구현합니다
        }
    }
    fun recommended_url(firestore:FirebaseFirestore,did:String,mymajor:String){
        // 선택된 디자이너의 프로필 사진을 띄웁니다
        firestore?.collection("hair_photo").whereEqualTo("id",did).whereEqualTo("style",mymajor).get()
            .addOnCompleteListener {
                var userDTO=ArrayList<photourl>()
                if(it.isSuccessful){
                    if(it.result!!.documents.size!=0){
                        Glide.with(this).load(it.result!!.documents[0]["url"]).into(clicyourstyle)
                        photomemo.text=it.result!!.documents[0]["memo"].toString()
                    }
                }else{
                    println("fail")
                }
            }
    }
    fun addmystyle(db: fireDB,id: String, did: String, url: String = "",like: Int,selecedit: SharedPreferences.Editor,){
        // 별표를 눌러서 까만별로 만들면 스크랩 기능을 구현하기 위해 db에 저장합니다.
        val firestore=db.firestore
        firestore?.collection("hair_mydesigner").whereEqualTo("customid",id)
            .whereEqualTo("id",did).get().addOnCompleteListener{
                if( it.result!!.documents.size!=0){
                    // 이미 즐겨찾기를 했더라면 (이미 까만 별이면) 하얀 별로 만들고 db에서 삭제합니다.
                    // 그리고 디자이너의 좋아요 수를 1 감소 시킵니다
                    firestore?.collection("hair_mydesigner").document(it.result!!.documents.get(0).id).delete()
                        .addOnCompleteListener {

                            isscrab.setImageResource(R.drawable.star_icon)
                            db.updateData_oneInt("hair_diary","like",like-1,did)
                            selecedit.putInt("like",like-1)
                            selecedit.apply()
                            Toast.makeText(this,"좋아요 취소",Toast.LENGTH_SHORT).show()

                        }
                }else{
                    // 까만 별로 만들고 db에 추가합니다.
                    // 그리고 디자이너의 좋아요 수를 1 증가 시킵니다

                    var userDTO=
                        designer(did,0,"",0,"",0,1,0,0,
                            "","","","",0,"","",url,
                            "","","","","","",id,like+1)
                    firestore?.collection("hair_mydesigner")?.document()?.set(userDTO)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                db.updateData_oneInt("hair_diary","like",like+1,did)
                                selecedit.putInt("like",like+1)
                                selecedit.apply()
                                isscrab.setImageResource(R.drawable.fullstart_icon)
                                Toast.makeText(this,"좋아요 추가",Toast.LENGTH_SHORT).show()}
                        }
                }
            }
    }

    fun isScrab(firestore:FirebaseFirestore,id:String,did:String){
        firestore?.collection("hair_mydesigner").whereEqualTo("customid",id).get()
            .addOnCompleteListener {
                //db에 접근하여 고객 아이디가 즐겨 찾기한 모든 리스트를 가져옵니다.
                if(it.isSuccessful){
                    Toast.makeText(this,"success",Toast.LENGTH_SHORT).show()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            if(it1.id==did){
                                // 그중 지금 디자이너의 아이디가 있다면 이미 좋아요를 했기에 까만 별로 표시합니다.
                                isscrab.setImageResource(R.drawable.fullstart_icon)
                            }// 그게 아니면 그냥 둡니다. default=투명별
                        }
                    }
                }else{
                    Toast.makeText(this,"fail",Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1->{
                var intent=Intent(this,Home2::class.java)
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
            else ->""
        }
        return true;
    }
    override fun onBackPressed() {
       // super.onBackPressed()

        var intent= Intent(this, Home2::class.java)
        startActivity(intent)
    }
    fun selectList(firestore: FirebaseFirestore, did: String,){
        // 선택된 디자이너가 올린 모든 사진을 봅니다.
        firestore?.collection("hair_photo").whereEqualTo("id",did).get()
            .addOnCompleteListener {
                var userDTO=ArrayList<photourl>()
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        dc.toObject(photourl::class.java)?.let { it1 -> userDTO.add(it1) }
                    }
                    var adapter = MyAdapter(applicationContext,R.layout.search_grid_adapter,userDTO)
                    // GridView 항목의 레이아웃 row.xml
                    detailed_grid.adapter = adapter
                    detailed_grid.setOnItemClickListener { parent, view, position, id ->
                        var item= detailed_grid.adapter.getItem(position) as photourl // 이걸로 getItem을 호출하는 것
                   //     println("${item.name}")// 이렇게 데이터 받을수 있고...
                    }
                    print("select list clear")
                }else{
                    println("fail")
                }
            }
    }
}
