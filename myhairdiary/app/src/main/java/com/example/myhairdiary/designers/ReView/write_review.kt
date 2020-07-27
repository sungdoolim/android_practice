package com.example.myhairdiary.designers.ReView

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myhairdiary.MainActivity
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_wirte_review.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_second.view.*

class write_review : AppCompatActivity() {
    val GALLERY=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wirte_review)
        var did=intent.getStringExtra("did")
        var reviewcount=intent.getIntExtra("reviewcount",-1)
        var pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","")
        var DRimgurl:String=""
        println("did : ${did}")

        review_designer_id.text=did.toString()
        review_submit.setOnClickListener(){
            var url=pref.getString("url","")
            var memo=review_memo.text.toString()
            println("review memo = ${memo}")
            var title=review_title.text
            println("review title : ${title}")

        createData(did,memo,Timestamp.now(),sesid!!,url.toString(),reviewcount)
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)



        }
        review_photo_up.setOnClickListener(){
openAlbum(did,reviewcount)
        }
    }

    public fun createData( did:String, memo:String,timestamp: Timestamp,customid:String,DRimgurl:String,count:Int){// 실제 되는거 확인 했음
        var userDTO=
            review_data(did,memo,timestamp,customid,DRimgurl,count)
        var firestore = FirebaseFirestore.getInstance()

        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_review")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
    }

    fun openAlbum(did:String="",reviewcount:Int=-1) {// 지금 designer아이디 받은거
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        println("openalbum : ${did}")
        val pref=getSharedPreferences("ins",0)
        val edit=pref.edit()
        edit.putString("did",did)
        edit.putInt("reviewcount",reviewcount)
        edit.putString("ssss","냐옹")
        edit.apply()
        startActivityForResult(intent, GALLERY)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GALLERY){
            val pref=getSharedPreferences("ins",0)
            var did=pref.getString("did","")// 디자이너 아이디임
            println(pref.getString("ssss",""))
            println("did  : ${did}")
            var photoUri=data?.data!!
            var sesid=pref.getString("id","null")
            var reviewcount= pref.getInt("reviewcount",-1)
            println("rvc : ${reviewcount}")
            review_album_imageview.setImageURI(photoUri)
            uploadPhoto(photoUri, sesid!!, reviewcount!!, did.toString(), 0)
//            if(customid!="") {
//                customidget(photoUri,sesid.toString()!!,index!!, customid.toString())
//            }else {
//                uploadPhoto(photoUri, sesid!!, index!!, "", 0)
//            }
        }
    }




    fun uploadPhoto(photoUri: Uri, sesid: String, reviewcount: Int,  did: String = "", customcount: Long) {
        //   var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var firestore = FirebaseFirestore.getInstance()
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
       var did2= pref.getString("did","").toString()
       // var reviewcount2=pref.getInt("reviewcount",0)
        // var customcount:Long=0
        //var fileName = "" + review// 파일 이름 지정 timestamp를 key로 해도...
println("did : ${did2}, rvc : ${reviewcount}")
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
            .child(did2).child("review").child(reviewcount.toString())// 여기 커스텀 아이디가 디자이너 아이디일꺼야

        var edit=pref.edit()
        storageRef.putFile(photoUri).addOnSuccessListener {

            storageRef.downloadUrl.addOnSuccessListener {
                uri->
                edit.putString("url",uri.toString())
                edit.putInt("reviewcount",reviewcount+1)
                edit.apply()
            }

            println("strageref : ${storageRef.toString()}")
            println("photoUri : ${photoUri.toString()}")
            var map = mutableMapOf<String, Any>()
                map["reviewcount"] = reviewcount + 1
            //디자이너 아이디=customid
                firestore?.collection("hair_diary").whereEqualTo("id",did).get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            var userDTO = ArrayList<review_data>()
                            for (dc in it.result!!.documents) {
                                dc.toObject(review_data::class.java)?.let { it1 -> userDTO.add(it1) }
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

            Toast.makeText(this, "Upload photo completed", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "reviewcount : ${reviewcount-1} reviewcount : ${reviewcount}", Toast.LENGTH_LONG).show()
        }
    }

}
