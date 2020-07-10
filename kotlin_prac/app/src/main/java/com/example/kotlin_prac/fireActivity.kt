package com.example.kotlin_prac

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class fireActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fire)
        var firestore = FirebaseFirestore.getInstance()
        //createData(firestore)
       // firestore?.collection("baby").document().set(firevo("캘시퍼","부산"))
        println("firestore")
        readQueryWhereEqulToData(firestore)
        updateData(firestore)
        deleteData(firestore)
    }
    private fun createData(firestore:FirebaseFirestore){// 실제 되는거 확인 했음
        var userDTO= firevo("범석","서울")
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("baby")?.document("document1")?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
    }
    private fun readQueryWhereEqulToData(firestore:FirebaseFirestore){
        println("read")
        firestore?.collection("baby").whereEqualTo("email","서울").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        var userDTO =dc.toObject(firevo::class.java)
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
