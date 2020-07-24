package com.example.myhairdiary.designers

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.myhairdiary.MainActivity
import com.example.myhairdiary.R
import com.example.myhairdiary.calendar.calist
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_portfolio.*
import java.io.*
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class portfolio : AppCompatActivity() {
val GALLERY=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portfolio)
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
       // var permis=pref.getString("perm","null")
        tractext.setOnClickListener(){
            var builder=AlertDialog.Builder(this)
            builder.setTitle("고객 사항???")
            val li=LinearLayout(this)
            li.setHorizontalGravity(1)

            val et= EditText(this)
            et.width=300
            et.hint="customer id"

            val et2= EditText(this)
            et2.width=300
            et2.hint="memo"
            li.addView(et)
            li.addView(et2)
            builder.setView(li).setPositiveButton("확인"){
                    dialogInterface,i->
                var memo=et2.text
                val customid=et.text
                registerTracText(memo.toString(),customid.toString())
            }.setNegativeButton("취소"){
                    dialogInterface,i-> ""
            }.show()


        }
        port_home.setOnClickListener(){
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        upload_photo.setOnClickListener(){
openAlbum()
            }
        delete_photo.setOnClickListener(){
            deletePhoto()
        }
        upload_photo_custom.setOnClickListener(){
            var builder=AlertDialog.Builder(this)
            builder.setTitle("고객의 아이디를 입력해 주세요")
            val et= EditText(this)
            builder.setView(et).setPositiveButton("확인"){
                dialogInterface,i->
                val customid=et.text
                openAlbum(customid.toString())
            }.setNegativeButton("취소"){
                    dialogInterface,i-> ""
            }.show()
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
            var youtubeurl=youurl.text.toString()
            var facebookurl=faceurl.text.toString()
            var instagramurl=instaurl.text.toString()
            var memo=Modmemo.text
            var year=Modyear.text
            val major=Modmajor.text
            val monthc=Integer.parseInt(Modmonthc.text.toString())
            val pref=getSharedPreferences("ins",0)
            var sesid=pref.getString("id","null")
            var firestore = FirebaseFirestore.getInstance()
            updateData(firestore,monthc,major.toString(),youtubeurl,facebookurl,instagramurl,memo.toString(),Integer.parseInt(year.toString()),sesid.toString())
        }


    }

    fun registerTracText(memo:String="",customid:String=""){

        var pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","")
        var count=pref.getInt("feedcount",0)

      var firestore=FirebaseFirestore.getInstance()
        val docData = hashMapOf(
            "id" to sesid,
            "customid" to customid,
            "memo" to memo,
            "timestamp" to Timestamp(Date()),
            "count" to count
        )
        var edit=pref.edit()
        edit.putInt("feedcount",count+1)
        edit.apply()

        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
            firestore?.collection("hair_feed")?.document()?.set(docData)
                .addOnCompleteListener {
                    if(it.isSuccessful)
                        print("create성공")
                    //println(docData["dateExample"])
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

    fun openAlbum(customid:String="") {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        println("openalbum : ${customid}")
        val pref=getSharedPreferences("ins",0)
        val edit=pref.edit()
        edit.putString("customid",customid)
        edit.apply()
        startActivityForResult(intent, GALLERY)
    }

    fun customidget( photoUri: Uri, sesid: String, index: Int,customid:String):Long{
        if(customid=="")
        { return 0}
        var firestore = FirebaseFirestore.getInstance()
        var customcount:Long=0
        var asy=firestore?.collection("hair_diary").whereEqualTo("id",sesid.toString()).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        if(dc.get(customid)==null){
                            var map= mutableMapOf<String,Any>()
                            map[customid] =0
                            customcount=0
                            firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        print("update")
                                        uploadPhoto(photoUri,sesid,index,customid,customcount)
                                        //   photoUri: Uri, sesid: String, index: Int, customid: String = "", customcount: Long
                                    }else{
                                        Log.d("fail","fail update........................................1")
                                    }
                                }
                        }else{
                            customcount= dc.get(customid) as Long
                            println("얻어와보자 ${customcount}")
                        uploadPhoto(photoUri,sesid,index,customid,customcount)//   photoUri: Uri, sesid: String, index: Int, customid: String = "", customcount: Long
                        }
                    }
                }else{
                    println("fail")
                }
            }
        return customcount
    }
    fun uploadPhoto(photoUri: Uri, sesid: String, index: Int, customid: String = "", customcount: Long) {
     //   var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var firestore = FirebaseFirestore.getInstance()
        val preff=getSharedPreferences("ins",0)
        var sesid=preff.getString("id","null")
       // var customcount:Long=0
        var fileName = sesid + "_." + index// 파일 이름 지정 timestamp를 key로 해도...
        if(customid!="") {
            fileName = sesid +"_"+customid+ "_." + customcount// 파일 이름 지정 timestamp를 key로 해도...
        }
        var storageRef = FirebaseStorage.getInstance().reference.child("images").child(fileName)
        val pref=getSharedPreferences("ins",0)
        var edit=pref.edit()
       // edit.putString("customid","")
        storageRef.putFile(photoUri).addOnSuccessListener {
            Toast.makeText(this, "Upload photo completed", Toast.LENGTH_SHORT).show()
            var map = mutableMapOf<String, Any>()
            if(customid=="") {
                edit.putString("index", (index + 1).toString())
                edit.apply()
                map["index"] = index + 1
                firestore?.collection("hair_diary").whereEqualTo("id", sesid).get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            var userDTO = ArrayList<designer>()
                            for (dc in it.result!!.documents) {
                                dc.toObject(designer::class.java)?.let { it1 -> userDTO.add(it1) }
                                firestore?.collection("hair_diary")
                                    .document(it.result!!.documents[0].id).update(map)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            print("update")
                                        } else {
                                            Log.d(
                                                "fail",
                                                "fail update........................................1"
                                            )
                                        }
                                    }
                            }
                        } else {
                            Log.d("fail", "fail update........................................")
                            println("fail")
                        }
                    }
            }
else {
                map[customid] = customcount + 1
                firestore?.collection("hair_diary").whereEqualTo("id", sesid).get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            var userDTO = ArrayList<designer>()
                            for (dc in it.result!!.documents) {
                                dc.toObject(designer::class.java)?.let { it1 -> userDTO.add(it1) }
                                firestore?.collection("hair_diary")
                                    .document(it.result!!.documents[0].id).update(map)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                        //   print("update")
                                        } else {
                                         //   Log.d("fail","fail update........................................1")
                                        }
                                    }
                            }
                        } else {
                        //    Log.d("fail", "fail update........................................")
                        //    println("fail")
                        }
                    }

            }
            Toast.makeText(this, "index : ${index-1} index : ${index}", Toast.LENGTH_LONG).show()
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
            val pref=getSharedPreferences("ins",0)
            var customid=pref.getString("customid","")
            println("onAct  : ${customid}")
            var photoUri=data?.data!!
            var sesid=pref.getString("id","null")
            var index= Integer.parseInt(pref.getString("index","0")!!)
            album_imageview.setImageURI(photoUri)
            if(customid!="") {
            customidget(photoUri,sesid.toString()!!,index!!, customid.toString())
            }else {
                uploadPhoto(photoUri, sesid!!, index!!, "", 0)
            }
            }
    }

    public fun updateData(firestore:FirebaseFirestore,  monthc:Int=0,  major:String="",youtubeurl:String="",
                          facebookurl:String="", instaurl:String="",  memo:String="",  year:Int=0, sesid:String=""){// 잘됨
             firestore?.collection("hair_diary").whereEqualTo("id",sesid).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    Log.d("success","success update........................................")
                    for(dc in it.result!!.documents){
                        //  println("${len+1} : ${dc.toString()}")
                        dc.toObject(designer::class.java)?.let { it1 -> userDTO.add(it1) }
                        // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        var map= mutableMapOf<String,Any>()
                        if(memo!=""){
                        map["memo"] =memo
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    print("update")
                                }else{
                                    Log.d("fail","fail update........................................1")
                                }
                            }
                        }
                        if(year!=0) {
                            map["year"] = year
                            //Log.d("1","${it.result!!.documents[0].toString()}")
                            //Log.d("11","${it.result!!.documents[0]["ref"].toString()}")
                            //Log.d("11","${it.result!!.documents[0].reference.toString()}")
                            // Log.d("11","${it.result!!.documents[0].metadata.toString()}")/
                         //Log.d("11", "${it.result!!.documents[0].id.toString()}")
                            firestore?.collection("hair_diary")
                                .document(it.result!!.documents[0].id).update(map)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                   //     print("update")
                                    } else {
                                  //      Log.d( "fail", "fail update........................................2"   )
                                    }
                                }
                        }
                        if(youtubeurl!=""){
                        map["youurl"]=youtubeurl
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                 //   print("update")
                                }else{
                                 //   Log.d("fail","fail update........................................2")
                                }
                            }}
                        if(facebookurl!=""){
                        map["faceurl"]=facebookurl
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                  //  print("update")
                                }else{
                                  //  Log.d("fail","fail update........................................2")
                                }
                            }}
                        if(instaurl!=""){
                        map["instaurl"]=instaurl
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                //    print("update")
                                }else{
                                  //  Log.d("fail","fail update........................................2")
                                }
                            }}
                        if(major!=""){
                        map["major"]=major
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id).update(map)
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                               //     print("update")
                                }else{
                                 //   Log.d("fail","fail update........................................2")
                                }
                            }}
                    if(monthc!=0) {
                        map["monthc"] = monthc
                        firestore?.collection("hair_diary").document(it.result!!.documents[0].id)
                            .update(map)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    print("update")
                                } else {
                                  //  Log.d( "fail",   "fail update........................................2"  )
                                }
                            }
                    }
                    }
                }else{
                 //   Log.d("fail","fail update........................................")
                    //println("fail")
                }
            }
    }
}
