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
     var real_photoUri: Uri? =null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_board)
        // 게시물을 올리는 폼 입니다.


        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        if(pref.getInt("perm",0)==0){
            // 권한에 따라 디자이너라면 고객 아이디를 입력하는 입력창이 나오게 됩니다.
            myboard_customid.visibility= View.INVISIBLE
        }else{
            myboard_customid.visibility= View.VISIBLE
        }
        val db= fireDB(this)


        myboard_back.setOnClickListener(){
            onBackPressed()
        }
        myboard_all.setOnClickListener(){
            // 버튼 클릭에 따라 이벤트를 처리했습니다. 효율적이지 않으나.... 라디오 버튼을 흉내내려 한 것 입니다.
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
            // 최종적으로 완료 버튼을 눌렀을때 입니다.
            // 서버에 저장합니다.

             title=myboard_title.text.toString()
             content=myboard_content.text.toString()
             reply=findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString()
             public=findViewById<RadioButton>(radioGroup2.checkedRadioButtonId).text.toString()
             search=findViewById<RadioButton>(radioGroup3.checkedRadioButtonId).text.toString()
             permission=findViewById<RadioButton>(radioGroup4.checkedRadioButtonId).text.toString()
            customid=myboard_customid.text.toString()
println("range : ${range} title : ${title}, content : ${content}, reply :${reply} " +
        "public : ${public} searh : ${search}, permisiont : ${permission}")


            //고객에게만 제공하는 사진 인지/ 포트폴리오 용으로 올리는 사진인지 happen변수를 사용해 구분합니다
            //1은 포트폴리오 용으로 전체 공개 입니다.
            //2는 고객의 다이어리 용입니다. 지금은 전체 공개로 되어있습니다.

            var happen=1
            var index=pref.getInt("index", -1)
            if(customid!=""){
                    happen=2
                }
            when(happen){
                // happen에 따라/ 사진 업로드를 하는지에 따른 구분입니다.

                1 -> {
                    if(real_photoUri!=null){
                        uploadPhoto(real_photoUri!!, index, title, "style", "length", "gender")
                    }else {
                        Toast.makeText(this,"사진 리뷰는 필수 입니다",Toast.LENGTH_SHORT).show()
                    }
                }
                2 -> {
                    if(real_photoUri!=null){
                        uploadPhoto_diary_to_Customer(real_photoUri!!, index, title, "style", "length", "gender",customid)
                    }else {
                        Toast.makeText(this,"사진 리뷰는 필수 입니다",Toast.LENGTH_SHORT).show()
                    }
                    }
                else-> {// 사실 이 경우는 없는것 같습니다.
                    if(real_photoUri!=null){
                        uploadPhoto_profile(real_photoUri!!)
                    }else {
                        Toast.makeText(this,"사진 리뷰는 필수 입니다",Toast.LENGTH_SHORT).show()
                    }}
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
                // 사진첩을 열고 사진 선택시 그 url을 저장합니다.

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
        storageRef=storageRef.child(id).child("profile")// 이렇게 경로를 지정하고

        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                // 지정한 경로에 사진이 성공적으로 올라가면 사진 정보를 따로 서버에 올립니다.
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