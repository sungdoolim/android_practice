package com.example.lovelist

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detail_coupon.*

class detail_coupon : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_coupon)
        val db=fireDB(this)
        val pref=getSharedPreferences("selected_coupon",0)
        val edit=pref.edit()
        val url=pref.getString("url","").toString()
        var rest=pref.getInt("rest",0)
        var index=pref.getInt("index",0)
        due.hint=pref.getString("due","").toString()
        println("index : ${index}")
        nowrest.hint=rest.toString()
        var cost=detail_content.text.toString()
        var nowrestR=nowrest.text.toString()
        Glide.with(this).load(url).into(couponimg)


        detail_mod.setOnClickListener(){




            var cost=detail_content.text.toString()
            var nowrestR=nowrest.text.toString()
            var due=due.text.toString()
            updaterest(db,rest,cost,nowrestR,index,edit,due)
            var intent=Intent(this,couponList::class.java)
            startActivity(intent)
        }
        detail_del.setOnClickListener(){
            var builder = AlertDialog.Builder(this)
            builder.setTitle("진짜 삭제 합니까?")
//            val li = LinearLayout(this)
//            li.setOrientation(LinearLayout.VERTICAL)
            builder.setPositiveButton("네 삭제합니다"){dialog: DialogInterface?, which: Int ->
                delcoupon(db,index)
                var intent=Intent(this,couponList::class.java)
                Toast.makeText(this,"삭제 합니다",Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }.setNegativeButton("취소"){dialog, which->
                Toast.makeText(this,"취소 합니다",Toast.LENGTH_SHORT).show()
            }.show()

//            delcoupon(db,index)
//            var intent=Intent(this,couponList::class.java)
//            startActivity(intent)
        }
    }

    fun updaterest(
        db: fireDB,
        rest: Int,
        cost: String,
        nowrest: String,
        index: Int,
        edit: SharedPreferences.Editor,
        due: String
    ){
        println("cost : ${cost}, nowrest: ${nowrest} ")
        println("cost : ${cost.length}")
        println("nowrest : ${cost.length}")
        if(cost!=""&&nowrest!=""){
    Toast.makeText(this,"바부야 둘다 수정을 어떻게 해애에엥",Toast.LENGTH_SHORT).show()

        }else if(cost==""&&nowrest!=""){

            var map= mutableMapOf<String,Any>()
            var map2= mutableMapOf<String,Any>()
            Toast.makeText(this,"쿠폰 값 재 설정!",Toast.LENGTH_SHORT).show()



            val firestore=db.firestore
            firestore?.collection("ourcoupon").whereEqualTo("index",index).get()
                .addOnCompleteListener{
                    map["rest"] =Integer.parseInt(nowrest)//(rest-Integer.parseInt(nowrest))
                    firestore?.collection("ourcoupon").document(it.result!!.documents[0].id).update(map)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                print("update")

                            }
                        }
                    if(due!=""){
                        map2["due"] =due
                        firestore?.collection("ourcoupon").document(it.result!!.documents[0].id).update(map2)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    print("update")
                                    edit.putString("due",due)
                                }
                            }
                    }
                }
        }else if(cost!=""&&nowrest==""){

            var map2= mutableMapOf<String,Any>()
            var map= mutableMapOf<String,Any>()
            Toast.makeText(this,"사용한 값 빼기",Toast.LENGTH_SHORT).show()
            val firestore=db.firestore
            firestore?.collection("ourcoupon").whereEqualTo("index",index).get()
                .addOnCompleteListener{
                    map["rest"] =rest-Integer.parseInt(cost)
                    firestore?.collection("ourcoupon").document(it.result!!.documents[0].id).update(map)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                print("update")
                                edit.putInt("rest",rest-Integer.parseInt(cost))
                            }
                        }
                    if(due!=""){
                        map2["due"] =due
                        firestore?.collection("ourcoupon").document(it.result!!.documents[0].id).update(map2)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    print("update")
                                    edit.putString("due",due)
                                }
                            }
                    }
                }

        }else{
            val firestore=db.firestore
            Toast.makeText(this,"뭐한 겨???",Toast.LENGTH_SHORT).show()
            if(due!=""){
                firestore?.collection("ourcoupon").whereEqualTo("index",index).get()
                    .addOnCompleteListener {
                        var map2 = mutableMapOf<String, Any>()
                        map2["due"] = due
                        firestore?.collection("ourcoupon").document(it.result!!.documents[0].id)
                            .update(map2)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    print("update")
                                    edit.putString("due", due)
                                }
                            }
                    }
            }

        }


    }

    fun delcoupon(db:fireDB,index:Int){
        val firestore=db.firestore
        firestore?.collection("ourcoupon").whereEqualTo("index",index).get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    firestore?.collection("ourcoupon").document(it.result!!.documents[0].id).delete()
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                print("delete")
                                FirebaseStorage.getInstance().reference.child("lovelist")
                                    .child(index.toString()).delete()
                                    .addOnSuccessListener {
                                        Toast.makeText(this, "Delete photo completed", Toast.LENGTH_LONG).show()
                                    }
                            }
                        }
                }
            }
    }


}