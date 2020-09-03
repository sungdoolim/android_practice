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
     var real_photoUri: Uri? =null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_designer_review)
        // 리뷰를 작성하고 firebase서버에 올리는 곳 입니다.

        val db= fireDB(this)
        val pref=getSharedPreferences("session", 0)

        val prefselected=getSharedPreferences("selected",0)
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

            if(real_photoUri!=null){
                // 사진을 등록했는지를 체크하며 사진이 없으면 리뷰작성이 되지 않습니다.
                upload_review(db, id,
                    real_photoUri!!, index, title, "style", "length", "gender", did)

                var intent= Intent(this, MyHairDiary::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this,"사진 리뷰는 필수 입니다",Toast.LENGTH_SHORT).show()

            }

          //  uploadPhoto_diary_to_Customer(real_photoUri, index, title, "style", "length", "gender",customid)

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
        // 저장할 firebase의 경로 입니다.

        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                // 사진을 저장하고 나서는 그 url주소를 따로 저장하게 됩니다.

                val pref=getSharedPreferences("selected", 0)
                var edit=pref.edit()
                db.insertReview_onephoto(uri.toString(), customid, index, title, style, length, gender,content,id,0,reply,range,search,permission,public)
                //사진을 저장후 그 url과 세부 데이터를 저장하는 부분입니다.

                edit.putInt("index", index + 1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()
        }

    }




}