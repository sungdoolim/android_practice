package com.example.myhairdiary_c.firedb

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*

class album(parent : Context): AppCompatActivity(){
    val GALLERY=0
    var pref=parent.getSharedPreferences("session",0)
    public fun openAlbum() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "*/*"
        println("openalbum ${pref.getString("id","")}")
        startActivityForResult(intent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GALLERY){
            var photoUri=data?.data!!
            album_imageview.setImageURI(photoUri)
            uploadPhoto_profile(photoUri)
        }
    }
    fun uploadPhoto_profile(photoUri: Uri) {
        var id=pref.getString("id","").toString()
        if(id==""){            return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child("profile")
        storageRef.putFile(photoUri).addOnSuccessListener {
            Toast.makeText(this, "index :", Toast.LENGTH_LONG).show()
        }
    }
}