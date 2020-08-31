package com.example.lovelist

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_insertpage.*

class insertpage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertpage)

        var firestore = FirebaseFirestore.getInstance()

        var pref=getSharedPreferences("Rnd",0)
        var edit=pref.edit()
        var id=pref.getString("id","").toString()
        loadindex(firestore,edit)



        gosubmit.setOnClickListener(){
            var place=goplace.text.toString()
            var content=gocontent.text.toString()
            var index=pref.getInt("index",0)
            println("index : ${index}")
            insert_onelist(firestore, place, content, index + 1, id,edit)
            var intent= Intent(this, unChecked::class.java)
            startActivity(intent)

        }
    }
    fun update_index(
        index: Int,
        firestore: FirebaseFirestore
    ){// 잘됨
        var map= mutableMapOf<String,Any>()
        map["index"] =index
        firestore?.collection("oursession").document("GVCRjP2s9Q9alROm03Rl").update(map)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    print("update")
                }else{
                    println("??????")
                }
            }
    }
    fun loadindex(
        firestore: FirebaseFirestore,
        edit: SharedPreferences.Editor
    ){

        firestore?.collection("oursession").get().addOnCompleteListener{
            if(it.isSuccessful){
                var userDTO=ArrayList<list_data>()
                for(dc in it.result!!.documents){
                    dc.toObject(list_data::class.java)?.let { it1 ->
                        // println("reviewcount : ${it1.reviewcount}")
                        userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                }
                println("designers  index = ${userDTO.get(0).index}")

                edit.putInt("index",userDTO.get(0).index)
                edit.apply()

                // recommend_designer_list 는 id로 얻어온 recyclerview 임

            }else{
                println("fail")
            }
        }
    }
    public fun insert_onelist(
        firestore: FirebaseFirestore,
        place: String,
        content: String,
        index: Int,
        id:String,
        edit: SharedPreferences.Editor
    ){// 실제 되는거 확인 했음
        var userDTO=
            list_data(place,content,index,id)

        var firestore = FirebaseFirestore.getInstance()
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함

        firestore?.collection("ourlist")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
                edit.putInt("index",index)
                edit.apply()
                update_index(index,firestore)

            }
    }
}