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
                        var intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        println("fail")
                    }
                }


        }
    }


}
