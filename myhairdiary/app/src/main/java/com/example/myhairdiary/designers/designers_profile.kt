package com.example.myhairdiary.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_designers_profile.*

class designers_profile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designers_profile)

//        val db= fireDB()
//        db.selectList()


        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore)

//        val designerLi=arrayListOf(
//
//            designer(R.drawable.ic_launcher_foreground,"1",1,"1111",25,"열심히 하겠습니다"),
//            designer(R.drawable.ic_launcher_foreground,"2",1,"2222",25,"열심히 하겠습니다"),
//            designer(R.drawable.ic_launcher_foreground,"3",1,"3333",25,"열심히 하겠습니다"),
//            designer(R.drawable.ic_launcher_foreground,"4",1,"4444",25,"열심히 하겠습니다")
//
//        )
//
//
//        designerlist.layoutManager=
//            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
//        designerlist.setHasFixedSize(true)
//        designerlist.adapter= designerAdapter(this,designerLi)



    }
    public fun selectList(firestore:FirebaseFirestore) {
        println("read")

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
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


                  //  val designerLi=userDTO


                    designerlist.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    designerlist.setHasFixedSize(true)
                    designerlist.adapter=
                        designerAdapter(
                            this,
                            userDTO
                        )



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
}
