package com.example.myhairdiary.social

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_face_b.*
import kotlinx.android.synthetic.main.activity_youtube.*

class youtube : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)
//
//        wvyoutube.settings.javaScriptEnabled=true
//        wvyoutube.webViewClient= WebViewClient()
//        wvyoutube.webChromeClient= WebChromeClient()

        val id=intent.getStringExtra("did")?:"null"
        readQueryWhereEqulToData(id)
        //wvyoutube.loadUrl("https://www.youtube.com/channel/UC_7dp9NRfrnuWEZNEPZbYlA")

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
                    //    wvyoutube.loadUrl(userDTO!!.youurl)

                        intent =  Intent(Intent.ACTION_VIEW, Uri.parse(userDTO!!.youurl));
                        startActivity(intent);
                    }
                }else{
                    println("fail")
                }
            }
        println("read end")
    }
    //https://www.youtube.com/channel/UC_7dp9NRfrnuWEZNEPZbYlA
}
