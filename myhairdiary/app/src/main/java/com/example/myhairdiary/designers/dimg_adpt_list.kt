package com.example.myhairdiary.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import kotlinx.android.synthetic.main.activity_dimg_adpt_list.*

class dimg_adpt_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dimg_adpt_list)

        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore)

    }
    public fun selectList(firestore:FirebaseFirestore) {
        println("read")
        val pref=getSharedPreferences("ins",0)
        var sesid=pref.getString("id","null")
        var max: Int = Integer.parseInt(pref.getString("index","0")!!)

        val profileList=ArrayList<Dimgs>()
        //    Dimgs(a,b,a),Dimgs(b,a,b),Dimgs(a,b,a)

        var url1=""
        var url2=""
        var url3=""

        for(i in 0..max-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images")
                .child(sesid + "_." + i.toString())
            println(i)
            println("dwurl : ${storageRef.downloadUrl}")
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                println("dwrul2 : ${uri}")

             //   loadPhoto(uri.toString(),i)

                println("i:${i}")
                when((i)%3){
                    0->url1=uri.toString()
                    1->url2=uri.toString()
                    2->{url3=uri.toString()
                        profileList.add(Dimgs(url1,url2,url3))}
                    else->""


                }
            }

        }
        println("max : ${max}")
        if(max%3!=2){
        profileList.add(Dimgs(url1,url2,url3))}

        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?" +
                "alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
var b=""
        profileList.add(Dimgs(a,a,a))

       // profileList.add(Dimgs(b,b,b))

        //profileList.add(Dimgs(a,a,a))
       // dimglistrv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)





        dimglistrv.setHasFixedSize(true)
        val mLayoutManager =  LinearLayoutManager(this);
        dimglistrv.layoutManager = mLayoutManager;
        dimglistrv.adapter=dimgAdapter(this,profileList)

        println("read end")

    }
}
