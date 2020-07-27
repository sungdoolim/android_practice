package com.example.myhairdiary.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_face_b.*
import kotlinx.android.synthetic.main.activity_insta.*

class insta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta)
        wvinsta.settings.javaScriptEnabled=true
        wvinsta.webViewClient= WebViewClient()
        wvinsta.webChromeClient= WebChromeClient()

        val id=intent.getStringExtra("did")?:"null"
        readQueryWhereEqulToData(id)
        //wvinsta.loadUrl("https://www.instagram.com/gimseongmin4250/?hl=ko")

    }
    public fun readQueryWhereEqulToData(id:String){
        println("read")
        var firestore = FirebaseFirestore.getInstance()
        firestore?.collection("hair_diary").whereEqualTo("id",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        var userDTO =dc.toObject(designer::class.java)
                        println("success ${userDTO.toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        wvinsta.loadUrl(userDTO!!.instaurl)
                    }
                }else{
                    println("fail")
                }
            }
        println("read end")
    }
}
