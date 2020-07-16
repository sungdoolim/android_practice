package com.example.myhairdiary.firedb

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.example.myhairdiary.designer
import com.example.myhairdiary.designers.designer_inter
import com.example.myhairdiary.register.Register
import com.google.firebase.firestore.FirebaseFirestore

class fireDB(){
    var firestore = FirebaseFirestore.getInstance()

    public fun createData( a:String, b:String){// 실제 되는거 확인 했음
        var userDTO= designer(a,1,b,1,"2",3,"1")
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_diary")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
    }
    public fun selectList() {
        println("read")

        firestore?.collection("hair_diary").get()
            .addOnCompleteListener {

                var len=0
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
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
                        designer_inter.designerL.add(a)
                        len++
                    }
                  //  return@addOnCompleteListener userDTO


                }else{
                    println("fail")
                }


            }
        println("read end")

    }
    public fun readQueryWhereEqulToData(){
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
    public fun updateData(){// 잘됨
        var map= mutableMapOf<String,Any>()
        map["mail"] ="staris"
        firestore?.collection("baby").document("document1").update(map)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    print("update")
                }
            }
    }
    public fun deleteData() {// 잘됨
        firestore?.collection("baby").document("document1").delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    print("delete")
                }
            }
    }
}