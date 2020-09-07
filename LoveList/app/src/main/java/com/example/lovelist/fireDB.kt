package com.example.lovelist

import android.content.Context
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage


class fireDB(parent: Context?){
    var firestore = FirebaseFirestore.getInstance()
    var parent=parent


    public fun updateData_oneInt(path:String,filed:String,value:Int){// 하나의 속성값을 바꿉니다
        var map= mutableMapOf<String,Any>()
        map[filed] =value
        firestore?.collection(path).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    firestore?.collection(path).document(it.result!!.documents[0].id).update(map)
                }
            }
    }

    public fun insert_onephoto(
        url: String = "",
        index:Int=0,
        due: String ="",
        rest: Int =0){
        // val url:String="",val id:String="",val pcount:Int=-1,val name:String=""
        var userDTO=
            list_data("","",index,"",false,url,due,rest)
        // photourl(url,id,pcount,name,style,length,gender,1,1,1,1,1,1,1)

        firestore?.collection("ourcoupon")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
        updateData_oneInt("oursession","index",index+1)
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
