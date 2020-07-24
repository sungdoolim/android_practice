package com.example.myhairdiary.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myhairdiary.MainActivity
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.example.myhairdiary.firedb.fireDB
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val db=fireDB()
        register_bt.setOnClickListener(){
            var id=etid.getText()
            var name=etpw.getText()
            db.createData(id.toString(),name.toString())
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        login_bt.setOnClickListener(){
            var intent= Intent(this, MainActivity::class.java)
            var id=etloginid.text
            val pref=getSharedPreferences("ins",0)
            var edit=pref.edit()
            edit.putString("id",id.toString())
            edit.apply()
            var firestore = FirebaseFirestore.getInstance()
            firestore?.collection("hair_diary").whereEqualTo("id",id.toString()).get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        for(dc in it.result!!.documents){
                            println("\nget test!!! : ${dc.getString("ttttt")}")
                            //  println("${len+1} : ${dc.toString()}")
                            edit.putString("perm",dc.toObject(designer::class.java)?.perm.toString())
                            edit.putString("index",dc.toObject(designer::class.java)?.index.toString())
                            edit.apply()
                        }
                    }else{
                        println("fail")
                    }
                }
            startActivity(intent)
        }
    }
    private fun createData(firestore: FirebaseFirestore, a:String, b:String){// 실제 되는거 확인 했음
        var userDTO= designer(
            a,
            1,
            b,
            1,
            "1",
            1,
            "memo 입니다"
        )
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_diary")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
    }
    private fun selectList(firestore:FirebaseFirestore){
        println("read")
        firestore?.collection("hair_diary").get()
            .addOnCompleteListener {
                var userDTO=ArrayList<designer>()
                var len=0
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                      //  println("${len+1} : ${dc.toString()}")
                        dc.toObject(designer::class.java)?.let { it1 -> userDTO.add(it1) }
                       // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        len++
                    }
                    print("select list clear")
                    len=0
                    for(a in userDTO){
                        print("${len} : ${a.name}")
                    }
                }else{
                    println("fail")
                }
            }
        println("read end")
    }
    private fun readQueryWhereEqulToData(firestore:FirebaseFirestore){
        println("read")
        firestore?.collection("hair_diary").whereEqualTo("email","서울").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        var userDTO =dc.toObject(designer::class.java)
                        println("success ${userDTO.toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                }else{
                    println("fail")
                }
            }
        println("read end")
        // firestore?.collection("User").whereGreaterThan("age",9).get()
        //firestore?.collection("User").whereGreaterThanOrEqualTo("age", 9).get()
        // firestore?.collection("User").whereLessThan("age",9).get()
        //   firestore?.collection("User").whereLessThanOrEqualTo("age",9).get()

    }
    private fun updateData(firestore:FirebaseFirestore){// 잘됨
        var map= mutableMapOf<String,Any>()
        map["mail"] ="staris"
        firestore?.collection("baby").document("document1").update(map)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    print("update")
                }
            }
    }
    private fun deleteData(firestore:FirebaseFirestore) {// 잘됨
        firestore?.collection("baby").document("document1").delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    print("delete")
                }
            }
    }
}
