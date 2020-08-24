package com.example.myhairdiary.test

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import com.example.myhairdiary.R
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_test_main.*

class testMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)

     //   https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234%2F4?alt=media&


        var controller =  MediaController(this);
        test_image4.setMediaController(controller);
        test_image4.requestFocus();
        var VIDEO_URL="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234%2F4?alt=media&"
        test_image4.setVideoURI(Uri.parse(VIDEO_URL));
test_image4.start()





     //   val storage = Firebase.storage
      //  val listRef = storage.reference.child("images")
        var listRef = FirebaseStorage.getInstance().reference.child("images").child("1234")
      //  Glide.with(this).load(listRef).into(test_image4)
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { prefix ->
              //      Glide.with(this).load(prefix.downloadUrl.toString()).into(test_image4)
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
//                    println(prefix.listAll().toString())
//                    println("foreachresult : ${prefix.downloadUrl}")
//                   println("foreachresult : ${prefix.name}")
//                    println("foreachresult : ${prefix.metadata}")
                }

                var kount=0
                listResult.items.forEach { item ->
                //    println(item.listAll().toString())
                    println("durl : ${item.downloadUrl}")
                   println("name : ${item.name}")
                    println("meta : ${item.metadata}")
                    println("path : ${item.path}")
                 //   println("${item.}")

                    // All the items under listRef.
                    item.downloadUrl.addOnSuccessListener {
var url=it.toString().split("token")
                        println(url[0].toString())
                        //Glide.with(this).load(it.toString()).into(test_image1)
                    }

                }
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
            }

    }
}
