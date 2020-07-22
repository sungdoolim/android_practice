package com.example.myhairdiary.calendar

import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.example.myhairdiary.designers.designerAdapter
import com.example.myhairdiary.designers.designer_inter
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_calbasic.*
import kotlinx.android.synthetic.main.activity_designers_profile.*
import kotlinx.android.synthetic.main.content_calbasic.*

class CalbasicActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calbasic)
      //  setSupportActionBar(toolbar)




        var pref=getSharedPreferences("ins",0)
        //var id=Integer.parseInt(pref.getString("dateint","0")!!)
        var sesid=pref.getString("id","0").toString()
        var dateint=Integer.parseInt(intent.getStringExtra("dateint")?:"0")
        println(dateint)
        println(sesid)
      //  var subitem=arrayOf("dd","22","dfsjl",1232,R.drawable.ic_launcher_background)
     //   var item=arrayOf(3,5,subitem,'q')



        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore,sesid,dateint)





    }
    public fun selectList(firestore:FirebaseFirestore,id:String,dateint:Int) {
        println("read")
        firestore.collection("hair_cal").whereEqualTo("id",id).whereEqualTo("date",dateint).get()
            .addOnCompleteListener {

                var len=0
                if(it.isSuccessful){
                    var userDTO=ArrayList<calist>()
                    for(dc in it.result!!.documents){
                        //  println("${len+1} : ${dc.toString()}")
                        dc.toObject(calist::class.java)?.let { it -> userDTO.add(it) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        len++
                        print("냐옹 해줘 ....${userDTO[0].customer}")
                    }
                    //  val designerLi=userDTO
                    todolist_re.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
                    todolist_re.setHasFixedSize(true)
                    todolist_re.adapter=
                        Cal_adapter(this, userDTO)
                }else{
                    println("fail")
                }


            }
        println("read end")

    }

}
