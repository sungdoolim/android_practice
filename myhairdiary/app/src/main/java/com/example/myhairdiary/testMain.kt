package com.example.myhairdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import kotlinx.android.synthetic.main.activity_test_main.*

class testMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)

     //   val storage = Firebase.storage
      //  val listRef = storage.reference.child("images")
        var listRef = FirebaseStorage.getInstance().reference.child("images")
        Glide.with(this).load(listRef).into(test_image4)
        listRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.prefixes.forEach { prefix ->
              //      Glide.with(this).load(prefix.downloadUrl.toString()).into(test_image4)
                    // All the prefixes under listRef.
                    // You may call listAll() recursively on them.
                 //   println(prefix.listAll().toString())
//                    println("foreachresult : ${prefix.downloadUrl}")
//                    println("foreachresult : ${prefix.name}")
//                    println("foreachresult : ${prefix.metadata}")
                }
//                var c="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.1?" +
//                        "alt=media&token=bc8a1336-2872-488c-8808-ea540f46cda1"
                listResult.items.forEach { item ->

                    // All the items under listRef.
                    item.downloadUrl.addOnSuccessListener {
                        Glide.with(this).load(it.toString()).into(test_image4)
                    }

                }
            }
            .addOnFailureListener {
                // Uh-oh, an error occurred!
            }

    }
}
