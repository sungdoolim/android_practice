package com.example.myhairdiary_c.designers

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.firedb.fireDB
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_designer_review.*
import kotlinx.android.synthetic.main.activity_designer_review.board_image
import kotlinx.android.synthetic.main.activity_designer_review.myboard_back
import kotlinx.android.synthetic.main.activity_designer_review.myboard_content
import kotlinx.android.synthetic.main.activity_designer_review.myboard_customid
import kotlinx.android.synthetic.main.activity_designer_review.myboard_savebt
import kotlinx.android.synthetic.main.activity_designer_review.myboard_title
import kotlinx.android.synthetic.main.activity_designer_review.myboard_upphoto


class designer_review : AppCompatActivity() {
    val GALLERY=0
    var title=""
    var content=""
    var range=""
    var reply=""
    var public=""
    var search=""
    var permission=""
    var customid=""
    lateinit var real_photoUri: Uri

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designer_review)

        val db= fireDB(this)
        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        val prefselected=getSharedPreferences("selected",0)
        val selecedit=prefselected.edit()
        val did=prefselected.getString("did","").toString()
        var index=prefselected.getInt("index", -1)
        val id=pref.getString("id","").toString()

        myboard_customid.text=did

        myboard_back.setOnClickListener(){
            onBackPressed()
        }
        myboard_upphoto.setOnClickListener(){
            openAlbum()
        }
        myboard_savebt.setOnClickListener(){
            title=myboard_title.text.toString()
            content=myboard_content.text.toString()
            println("range : ${range} title : ${title}, content : ${content}, reply :${reply} " +
                    "public : ${public} searh : ${search}, permisiont : ${permission}")


            upload_review(db,id,real_photoUri, index, title, "style", "length", "gender",did)
          //  uploadPhoto_diary_to_Customer(real_photoUri, index, title, "style", "length", "gender",customid)





            var intent= Intent(this, MyHairDiary::class.java)
            startActivity(intent)
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
                board_image.setImageURI(photoUri)
            }else{
                return
            }
        }
    }

    fun upload_review(db:fireDB,id:String,        photoUri: Uri,        index: Int,        title: String,        style: String,        length: String,
        gender: String,        customid: String    ) {
        // 디자이너 폴더 -> customer 폴더를 만들고 그 안에 사진을 올립니다.


        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images").child("review")
        storageRef=storageRef.child(customid).child(id).child(title)
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                val pref=getSharedPreferences("selected", 0)
                var edit=pref.edit()
                db.insertReview_onephoto(uri.toString(), customid, index, title, style, length, gender,content,id,0,reply,range,search,permission,public)
                edit.putInt("index", index + 1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()
        }

    }




}