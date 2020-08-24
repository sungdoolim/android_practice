package com.example.myhairdiary_c.firedb

import android.content.Context
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designer_inter
import com.example.myhairdiary_c.designers.photourl
import com.google.firebase.firestore.FirebaseFirestore

class fireDB(parent: Context?){
    var firestore = FirebaseFirestore.getInstance()
    var parent=parent
    public fun createData( a:String, b:String){
        // 데이터를 삽입합니다.
        var userDTO=
            designer(a, 0, b, 0, "", 0)
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_diary")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
    }

    public fun updateData_one(path:String,filed:String,value:String,id:String){// 하나의 속성값을 바꿉니다
        var map= mutableMapOf<String,Any>()
        map[filed] =value
        firestore?.collection(path).whereEqualTo("id",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                }
            }
    }
    public fun updateData_oneInt(path:String,filed:String,value:Int,id:String){// 하나의 속성값을 바꿉니다
        var map= mutableMapOf<String,Any>()
        map[filed] =value
        firestore?.collection(path).whereEqualTo("id",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                }
            }
    }
    public fun updateData_oneInt_url(path:String,filed:String,value:Int,url:String){// 하나의 속성값을 바꿉니다
        var map= mutableMapOf<String,Any>()
        map[filed] =value
        firestore?.collection(path).whereEqualTo("url",url).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    firestore?.collection(path).document(it.result!!.documents[0].id).update(map)
                }
            }
    }
    public fun insert_onephoto(  url: String = "", id: String = "", pcount: Int = -1, name: String = "", style: String = "",
        length: String="",  gender:String="",){
       // val url:String="",val id:String="",val pcount:Int=-1,val name:String=""
        var userDTO=
            photourl(url,id,pcount,name,style,length,gender)

        firestore?.collection("hair_photo")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
        updateData_oneInt("hair_diary","index",pcount+1,id)

    }
    public fun deleteData() {// 잘됨
        firestore?.collection("baby").document("document1").delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    print("delete")
                }
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
}
