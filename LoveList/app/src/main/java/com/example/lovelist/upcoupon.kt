package com.example.lovelist

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_un_checked.*
import kotlinx.android.synthetic.main.activity_upcoupon.*
import kotlinx.android.synthetic.main.bottom_nav.*

class upcoupon : AppCompatActivity() {
    val GALLERY=0
    var title=""
    var content=""
    var range=""
    var reply=""
    var public=""
    var search=""
    var permission=""
    var customid=""
    var real_photoUri: Uri? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoupon)


        var firestore = FirebaseFirestore.getInstance()

        var pref=getSharedPreferences("Rnd",0)
        var edit=pref.edit()

        loadindex(firestore,edit)



        var index=pref.getInt("index",-1)
        openalbum.setOnClickListener(){
            openAlbum()
        }
        submit.setOnClickListener(){
            var r=rest.text.toString()
            val due=due.text.toString()
            var rest = 0
            if(r!=""){
                rest=Integer.parseInt(r)
            }

            if(real_photoUri!=null){
                uploadPhoto(real_photoUri!!,index+1,due,rest)
                val intent=Intent(this,couponList::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"사진 리뷰는 필수 입니다", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun loadindex(
        firestore: FirebaseFirestore,
        edit: SharedPreferences.Editor
    ){

        firestore?.collection("oursession").get().addOnCompleteListener{
            if(it.isSuccessful){
                var userDTO=ArrayList<list_data>()
                for(dc in it.result!!.documents){
                    dc.toObject(list_data::class.java)?.let { it1 ->
                        // println("reviewcount : ${it1.reviewcount}")
                        userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                }
                println("designers  index = ${userDTO.get(0).index}")

                edit.putInt("index",userDTO.get(0).index)
                edit.apply()

                // recommend_designer_list 는 id로 얻어온 recyclerview 임

            }else{
                println("fail")
            }
        }
    }

    fun uploadPhoto(photoUri: Uri, index: Int, due: String, rest: Int) {
        // 디자이너가 사진을 올립니다. 포트폴리오용 입니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("Rnd", 0)
        var edit=pref.edit()
        var storageRef = FirebaseStorage.getInstance().reference.child("lovelist")
        storageRef=storageRef.child(index.toString())

        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->

                db.insert_onephoto(uri.toString(),index,due,rest)
                edit.putInt("index", index + 1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()

        }
    }
    public fun openAlbum() {
        // 휴대폰 내에 앨범을 엽니다.
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val pref=getSharedPreferences("session", 0)
        println("openalbum ${pref.getString("id", "")}")
        startActivityForResult(intent, GALLERY)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
// 앨범이 선택되면 실행되는 듯 합니다.
        println("request code : ${requestCode}")
        println("result code : ${resultCode}")
        println("data : ${data.toString()}")

        if(requestCode==GALLERY){
            if(data!=null) {
                var photoUri = data?.data!!
                real_photoUri = photoUri
                imageView.setImageURI(photoUri)
            }else{
                return
            }
        }
    }
}