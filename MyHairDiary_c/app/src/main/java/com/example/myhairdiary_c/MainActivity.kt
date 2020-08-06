package com.example.myhairdiary_c

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designer_list
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val GALLERY=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref=getSharedPreferences("session",0)
        var edit=pref.edit()
        val db= fireDB(this)
        register_bt.setOnClickListener(){
            var id=etid.getText()
            var name=etpw.getText()
            db.createData(id.toString(),name.toString())
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        login_bt.setOnClickListener(){
            var id=etloginid.text

            edit.putString("id",id.toString())
            edit.apply()
            var firestore = FirebaseFirestore.getInstance()
            firestore?.collection("hair_diary").whereEqualTo("id",id.toString()).get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        for(dc in it.result!!.documents){
                            println("\nget test!!! : ${dc.getString("ttttt")}")
                            //  println("${len+1} : ${dc.toString()}")
                            edit.putString("perm",dc.toObject(designer::class.java)?.perm.toString())
                            edit.putString("name",dc.toObject(designer::class.java)?.name.toString())
                            edit.putInt("year", dc.toObject(designer::class.java)?.year!!.toInt())
                            edit.putInt("index",dc.toObject(designer::class.java)?.index!!.toInt())
                            edit.putString("memo",dc.toObject(designer::class.java)?.memo.toString())
                            edit.putString("phone",dc.toObject(designer::class.java)?.phone.toString())
                            edit.putInt("age",dc.toObject(designer::class.java)?.age!!.toInt())
                            edit.putString("profile",dc.toObject(designer::class.java)?.profile.toString())

                            edit.apply()
                        }
                        var intent= Intent(this, Home2::class.java)
                        startActivity(intent)
                    }else{
                        println("fail")
                    }
                }
        }
        profilephoto.setOnClickListener(){
            edit.putInt("happen",0)
            edit.apply()
         openAlbum()
        }
        upphoto.setOnClickListener(){
            edit.putInt("happen",1)
            edit.apply()
            openAlbum()
        }
        upphoto_customer.setOnClickListener(){
            edit.putInt("happen",2)
            edit.apply()
            openAlbum()
        }
        dlist.setOnClickListener(){
            var intent= Intent(this, designer_list::class.java)
            startActivity(intent)
        }
    }






    public fun openAlbum() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val pref=getSharedPreferences("session",0)
        println("openalbum ${pref.getString("id","")}")
        startActivityForResult(intent, GALLERY)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val pref=getSharedPreferences("session",0)
        var happen=pref.getInt("happen",0)
        var index=pref.getInt("index",-1)
        if(requestCode==GALLERY){
            var photoUri=data?.data!!
            //album_imageview.setImageURI(photoUri)
            when(happen){
                1-> {
                    var builder=AlertDialog.Builder(this)
                    builder.setTitle("사진 올리기")
                    val li= LinearLayout(this)
                   // li.setHorizontalGravity(1)

                    //li.setVerticalGravity(1)
                    li.setOrientation(LinearLayout.VERTICAL)
                    val et= EditText(this)
                    et.width=300
                    et.hint="제목"

                    val et2= EditText(this)
                    et2.width=300
                    et2.hint="스타일 : (~컷)"
                    val et3=EditText(this)
                    et3.width=300
                    et3.hint="기장 :(단발,장발,중간)"
                    val et4=EditText(this)
                    et4.width=300
                    et4.hint="성별"
                    li.addView(et)
                    li.addView(et2)
                    li.addView(et3)
                    li.addView(et4)
                    builder.setView(li).setPositiveButton("확인"){
                            dialogInterface,i->
                        var style=et2.text.toString()
                        val name=et.text.toString()
                        val length=et3.text.toString()
                        val gender=et4.text.toString()
                   //     registerTracText(memo.toString(),customid.toString())
                        uploadPhoto(photoUri,index,name,style,length,gender)
                    }.setNegativeButton("취소"){
                            dialogInterface,i-> ""
                    }.show()

                }
                2-> uploadPhoto_diary_to_Customer(photoUri)
                else-> uploadPhoto_profile(photoUri)
            }

        }
    }
    fun uploadPhoto_profile(photoUri: Uri) {
var db=fireDB(this)
        val pref=getSharedPreferences("session",0)
        val edit=pref.edit()
        var id=pref.getString("id","").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child("profile")
        storageRef.putFile(photoUri).addOnSuccessListener {
           storageRef.downloadUrl.addOnSuccessListener { uri->
               db.updateData_one("profile",uri.toString(),id)
               edit.putString("profile",uri.toString())
               edit.apply()

           }
            Toast.makeText(this, "프로필 바꾸기 성공", Toast.LENGTH_LONG).show()


            //
        }

    }
    fun deletePhoto() { // 이거 삭제인데 수정은 그냥 같은 이름으로 putfile하면 되는 거잖아???

        FirebaseStorage.getInstance().reference.child("images")
            .child("").delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Delete photo completed", Toast.LENGTH_LONG).show()
            }
    }




    fun uploadPhoto_diary_to_Customer(photoUri: Uri) {
        var db=fireDB(this)
        val pref=getSharedPreferences("session",0)
        var id=pref.getString("id","").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child("profile")
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                db.updateData_one("profile",uri.toString(),id)
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()


            //
        }

    }
    fun uploadPhoto(
        photoUri: Uri,
        index: Int,
        name: String,
        style: String,
        length: String,
        gender:String
    ) {
        var db=fireDB(this)
        val pref=getSharedPreferences("session",0)
        var edit=pref.edit()
        var id=pref.getString("id","").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child(name)//Timestamp(java.util.Date()).toString()
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                //url:String="",id:String="",pcount:Int=-1,name:String="")
                db.insert_onephoto(uri.toString(),id,index,name,style,length,gender)
                edit.putInt("index",index+1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()
            //
        }
    }
}
