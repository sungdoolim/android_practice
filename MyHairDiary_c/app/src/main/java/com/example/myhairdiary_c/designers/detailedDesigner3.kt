package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.style.MyAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detailed_designer3.*
import kotlinx.android.synthetic.main.detailed_designer_uppertab.*

class detailedDesigner3 : AppCompatActivity() {

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_designer3)
      //  bt3.setBackgroundColor(R.color.colorAccent)
        val prefselected=getSharedPreferences("selected",0)
        var did=prefselected.getString("did","")
        selectList(did!!)
        bt1.setOnClickListener(){
            var intent= Intent(this, detailedDesigner::class.java)
            startActivity(intent)

        }
        bt2.setOnClickListener(){
            var intent= Intent(this, detailedDesigner2::class.java)
            startActivity(intent)
        }
        bt4.setOnClickListener(){
            var intent= Intent(this, detailedDesigner4::class.java)
            startActivity(intent)
        }

    }
    public fun selectList(did: String) {

        //하.... 나중에 개선 합시다....
        var firestore= FirebaseFirestore.getInstance()

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
                        style_grid.adapter = adapter
                        style_grid.setOnItemClickListener { parent, view, position, id ->
                            var item= style_grid.adapter.getItem(position) as photourl
                            println("${item.name}")// 이렇게 데이터 받을수 있고...
                        }
                        print("select list clear")
                    }else{
                        println("fail")
                    }
                }



    }
}
