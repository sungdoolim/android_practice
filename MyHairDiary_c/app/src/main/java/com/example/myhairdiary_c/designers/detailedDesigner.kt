package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.style.MyAdapter
import kotlinx.android.synthetic.main.activity_detailed_designer.*
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer)
        bt1.setBackgroundColor(R.color.colorAccent)
        val prefselected=getSharedPreferences("selected",0)

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





        var url=prefselected.getString("profile","")
        var did=prefselected.getString("did","")
//intent.getStringExtra("profile")
        Glide.with(this).load(url).into(designer_photo)
        designer_photo.setBackground(ShapeDrawable(OvalShape()));
        designer_photo.setClipToOutline(true)
        selectList(did!!)

        review_imgbt.setOnClickListener(){
            Toast.makeText(this,"toast!",Toast.LENGTH_SHORT).show()
        }


    }
    fun selectList(did:String){
        var firestore= fireDB(this).firestore
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