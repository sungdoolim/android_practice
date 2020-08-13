package com.example.lovelist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_list.*

class detail_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_list)

        val firestore=FirebaseFirestore.getInstance()
        val pref=getSharedPreferences("selected",0)
        var index=pref.getInt("index",-1)
        val ischecked=pref.getBoolean("ischecked",false)
        iscpt.isChecked=ischecked

        detail_place.hint="어디서~? "+pref.getString("place","")
        detail_content.hint="뭐하고싶어~? "+pref.getString("content","")


        detail_del.setOnClickListener(){
        deleteData(firestore,index)
            Toast.makeText(this,"삭제 중~ 좀만 기다려주세용~",Toast.LENGTH_SHORT).show()

        }
        detail_mod.setOnClickListener(){
            var ischec=iscpt.isChecked
            var place=detail_place.text.toString()
            var content=detail_content.text.toString()
            println("1111place : ${place}, content : ${content}")

            if(place==""){place=detail_place.hint.toString().split(" ")[1]}
            if(content==""){content=detail_content.hint.toString().split(" ")[1]}
            println("2222place : ${place}, content : ${content}")
            modeData(firestore,place,content,index,ischec)

            Toast.makeText(this,"변경중~ 좀만 기다려주세용~",Toast.LENGTH_SHORT).show()

        }

    }
    fun completeData(firestore:FirebaseFirestore,index :Int){
        var map= mutableMapOf<String,Any>()
        firestore?.collection("ourlist").whereEqualTo("index",index).get()
            .addOnCompleteListener {

                map["ischecked"] =true
                firestore?.collection("ourlist").document(it.result!!.documents[0].id).update(map)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            print("update")
                        }
                    }
                var intent= Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

    }
    fun modeData(firestore:FirebaseFirestore,place:String,content:String,index:Int,ischec:Boolean){

        var map= mutableMapOf<String,Any>()
        var map2= mutableMapOf<String,Any>()

        var map3= mutableMapOf<String,Any>()

        firestore?.collection("ourlist").whereEqualTo("index",index).get()
            .addOnCompleteListener {

                map["place"] =place
                firestore?.collection("ourlist").document(it.result!!.documents[0].id).update(map)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            print("update")
                        }
                    }
                map2["content"] =content
                firestore?.collection("ourlist").document(it.result!!.documents[0].id).update(map2)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            print("update")
                        }
                    }
                map3["ischecked"] =ischec
                firestore?.collection("ourlist").document(it.result!!.documents[0].id).update(map3)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            print("update")
                        }
                    }

                var intent= Intent(this, MainActivity::class.java)
                startActivity(intent)

            }


    }
    fun deleteData(firestore: FirebaseFirestore,index:Int) {// 잘됨


        firestore?.collection("ourlist").whereEqualTo("index",index).get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    firestore?.collection("ourlist").document(it.result!!.documents[0].id).delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                print("delete")
                            }
                        }
                    var intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}
