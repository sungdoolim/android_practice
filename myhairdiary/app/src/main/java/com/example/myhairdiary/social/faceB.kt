package com.example.myhairdiary.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_face_b.*

class faceB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_b)
        wvface.settings.javaScriptEnabled=true
        wvface.webViewClient= WebViewClient()
        wvface.webChromeClient= WebChromeClient()
        val id=intent.getStringExtra("id")?:"null"
        readQueryWhereEqulToData(id)
        //wvface.loadUrl("https://www.facebook.com/profile.php?id=100011043121912")
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
                        wvface.loadUrl(userDTO!!.faceurl)
                    }
                }else{
                    println("fail")
                }
            }
        println("read end")
    }
}
