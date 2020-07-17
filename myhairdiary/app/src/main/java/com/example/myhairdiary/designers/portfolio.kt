package com.example.myhairdiary.designers

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.bumptech.glide.Glide
import com.example.myhairdiary.R
import com.example.myhairdiary.designer
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.ktx.storageMetadata
import com.google.protobuf.compiler.PluginProtos
import kotlinx.android.synthetic.main.activity_portfolio.*
import java.io.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class portfolio : AppCompatActivity() {
val GALLERY=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)



        upload_photo.setOnClickListener(){
openAlbum()
            }
        delete_photo.setOnClickListener(){
            deletePhoto()
        }
load_photo.setOnClickListener(){
    var storageRef = FirebaseStorage.getInstance().reference.child("images").child(load_filename_edittext.text.toString())
    storageRef.downloadUrl.addOnSuccessListener { uri ->
        loadPhoto(uri.toString())
    }

}

        download_photo.setOnClickListener(){
            var storageRef = FirebaseStorage.getInstance().reference.child("images").child(download_filename_edittext.text.toString())
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                DownloadFileFromURL().execute(uri.toString())
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            }
        }

        modify.setOnClickListener(){
            var memo=Modmemo.text
            var year=Modyear.text

            val pref=getSharedPreferences("ins",0)

            var sesid=pref.getString("id","null")
            Log.d("id````````````````````",sesid)

            var firestore = FirebaseFirestore.getInstance()
            updateData(firestore,memo.toString(),Integer.parseInt(year.toString()),sesid.toString())
        }


    }
    inner class DownloadFileFromURL : AsyncTask<String?, String?, String?>() {

        override fun doInBackground(vararg p0: String?): String? {
            //file download path
            val downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()

            //image download url
            val url = URL(p0[0])
            val conection = url.openConnection()
            conection.connect()

            // input stream to read file - with 8k buffer
            val input = BufferedInputStream(url.openStream(), 8192)

            // output stream to write file
            val output = FileOutputStream(downloadFolder + "/howl_Dfile.jpg")
            val data = ByteArray(1024)
            var total = 0L

            // writing data to file
            var count : Int
            while (input.read(data).also { count = it } != -1) {
                total += count.toLong()

                output.write(data, 0, count)
            }
            // flushing output
            output.flush()
            // closing streams
            output.close()
            input.close()

            return null
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Toast.makeText(this@portfolio,"download file completed",Toast.LENGTH_LONG).show()
        }
    }
    fun loadPhoto(downloadUrl : String) {
        Glide.with(this).load(downloadUrl).into(album_imageview)
    }

    fun openAlbum() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }

    fun uploadPhoto(photoUri: Uri) {
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var fileName = "IMAGE_" + timestamp + "_.png"// 파일 이름 지정 timestamp를 key로 해도...

        var storageRef = FirebaseStorage.getInstance().reference.child("images").child(fileName)

        storageRef.putFile(photoUri).addOnSuccessListener {
            Toast.makeText(this, "Upload photo completed", Toast.LENGTH_LONG).show()
        }
    }
    fun deletePhoto() {
        FirebaseStorage.getInstance().reference.child("images")
            .child(delete_filename_edittext.text.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Delete photo completed", Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GALLERY){
            var photoUri=data?.data!!
            album_imageview.setImageURI(photoUri)
            uploadPhoto(photoUri)
        }
    }

    public fun updateData(firestore:FirebaseFirestore,memo:String,year:Int,sesid:String){// 잘됨
             firestore?.collection("hair_diary").whereEqualTo("id",sesid).get()
            .addOnCompleteListener {

                var len=0
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    Log.d("success","success update........................................")
                    for(dc in it.result!!.documents){
                        //  println("${len+1} : ${dc.toString()}")
                        dc.toObject(designer::class.java)?.let { it1 -> userDTO.add(it1) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        var map= mutableMapOf<String,Any>()
                        map["memo"] =memo

                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{

                                    Log.d("fail","fail update........................................1")
                                }
                            }
                        map["year"]=year
                        //Log.d("1","${it.result!!.documents[0].toString()}")
                        //Log.d("11","${it.result!!.documents[0]["ref"].toString()}")
                        //Log.d("11","${it.result!!.documents[0].reference.toString()}")
                       // Log.d("11","${it.result!!.documents[0].metadata.toString()}")
                        Log.d("11","${it.result!!.documents[0].id.toString()}")
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{

                                    Log.d("fail","fail update........................................2")
                                }
                            }

                    }
                }else{
                    Log.d("fail","fail update........................................")
                    println("fail")
                }


            }






    }

}
