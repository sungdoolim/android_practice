package com.example.myhairdiary_c.diary

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.firedb.fireDB
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_my_board.*

class MyBoard : AppCompatActivity() {
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
        setContentView(R.layout.activity_my_board)
        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        if(pref.getInt("perm",0)==0){
            myboard_customid.visibility= View.INVISIBLE
        }else{
            myboard_customid.visibility= View.VISIBLE
        }
        val db= fireDB(this)


        myboard_back.setOnClickListener(){
            onBackPressed()
        }
        myboard_all.setOnClickListener(){
            var param=ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,50)



            range="전체"
            myboard_all.setTextColor(Color.parseColor("#A9E3FD"))
            myboard_neighb.setTextColor(Color.BLACK)
            myboard_neineigh.setTextColor(Color.BLACK)
            myboard_private.setTextColor(Color.BLACK)
            Toast.makeText(this,"position "+range,Toast.LENGTH_SHORT).show()
        }
        myboard_neighb.setOnClickListener(){
            range="이웃"
            myboard_all.setTextColor(Color.BLACK)
            myboard_neighb.setTextColor(Color.parseColor("#A9E3FD"))
            myboard_neineigh.setTextColor(Color.BLACK)
            myboard_private.setTextColor(Color.BLACK)
            Toast.makeText(this,"position "+range,Toast.LENGTH_SHORT).show()
        }
        myboard_neineigh.setOnClickListener(){
            range="서로이웃"
            myboard_all.setTextColor(Color.BLACK)
            myboard_neighb.setTextColor(Color.BLACK)
            myboard_neineigh.setTextColor(Color.parseColor("#A9E3FD"))
            myboard_private.setTextColor(Color.BLACK)
            Toast.makeText(this,"position "+range,Toast.LENGTH_SHORT).show()
        }
        myboard_private.setOnClickListener(){
            range="비공개"
            myboard_all.setTextColor(Color.BLACK)
            myboard_neighb.setTextColor(Color.BLACK)
            myboard_neineigh.setTextColor(Color.BLACK)
            myboard_private.setTextColor(Color.parseColor("#A9E3FD"))
            Toast.makeText(this,"position "+range,Toast.LENGTH_SHORT).show()
        }

        myboard_upphoto.setOnClickListener(){
            openAlbum()
        }
        myboard_savebt.setOnClickListener(){
             title=myboard_title.text.toString()
             content=myboard_content.text.toString()
             reply=findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
             public=findViewById<RadioButton>(radioGroup2.checkedRadioButtonId).text.toString()
             search=findViewById<RadioButton>(radioGroup3.checkedRadioButtonId).text.toString()
             permission=findViewById<RadioButton>(radioGroup4.checkedRadioButtonId).text.toString()
            customid=myboard_customid.text.toString()
println("range : ${range} title : ${title}, content : ${content}, reply :${reply} " +
        "public : ${public} searh : ${search}, permisiont : ${permission}")

            var happen=1
            var index=pref.getInt("index", -1)

            if(customid!=""){
                    happen=2
                }
            when(happen){
                1 -> {
                    uploadPhoto(real_photoUri, index, title, "style", "length", "gender")
                }
                2 -> uploadPhoto_diary_to_Customer(real_photoUri, index, title, "style", "length", "gender",customid)
                else-> uploadPhoto_profile(real_photoUri)
            }
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
    fun uploadPhoto_profile(photoUri: Uri) {
        // 프로필 사진을 올리고, 그 url을 profile필드에 저장합니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("session", 0)
        val edit=pref.edit()
        var id=pref.getString("id", "").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child("profile")
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                db.updateData_one("hair_diary", "profile", uri.toString(), id)
                edit.putString("profile", uri.toString())
                edit.apply()

            }
            Toast.makeText(this, "프로필 바꾸기 성공", Toast.LENGTH_LONG).show()

        }

    }

    fun uploadPhoto_diary_to_Customer(
        photoUri: Uri,
        index: Int,
        title: String,
        style: String,
        length: String,
        gender: String,
        customid: String
    ) {
        // 디자이너 폴더 -> customer 폴더를 만들고 그 안에 사진을 올립니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("session", 0)
        var id=pref.getString("id", "").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child(customid).child(title)
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                val pref=getSharedPreferences("session", 0)
                var edit=pref.edit()
                db.insert_onephoto(uri.toString(), id, index, title, style, length, gender,content,customid,0,reply,range,search,permission,public)
                edit.putInt("index", index + 1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()
        }

    }
    //val url:String="",val id:String="",val pcount:Int=-1,
    //                    val name:String="",val style : String="",val length:String="",val gender:String="",
    //                    val memo:String="",val customid:String="",val like:Int=0,
    //                    val per_reply:String="",
    //                    val range:String="",
    //                    val per_search:String="",
    //                    val permission:String="",


    fun uploadPhoto(photoUri: Uri, index: Int, name: String, style: String, length: String, gender: String,
    ) {
        // 디자이너가 사진을 올립니다. 포트폴리오용 입니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        var id=pref.getString("id", "").toString()
        if(id==""){return;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child(name)//Timestamp(java.util.Date()).toString()
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                //url:String="",id:String="",pcount:Int=-1,name:String="")

                //photourl(url,id,pcount,name,style,length,gender,1,1,1,1,1,1,1)
                db.insert_onephoto(uri.toString(), id, index, name, style, length, gender,content,"",0,reply,range,search,permission,public)
                edit.putInt("index", index + 1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()

        }
    }

}