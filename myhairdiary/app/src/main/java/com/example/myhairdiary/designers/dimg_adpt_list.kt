package com.example.myhairdiary.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import kotlinx.android.synthetic.main.activity_dimg_adpt_list.*
import kotlinx.coroutines.runBlocking

class dimg_adpt_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dimg_adpt_list)

        var firestore = FirebaseFirestore.getInstance()


            designer_img_inter.selectList_interface(firestore,this, View(this))}


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
         //   println("dwurl : ${storageRef.downloadUrl}")
           var asy= storageRef.downloadUrl.addOnSuccessListener { uri ->
          //      println("dwrul2 : ${uri}")
             //   loadPhoto(uri.toString(),i)
                println("i:${i}, max : ${max}")
                when((i)%3){
                    0->{url1=uri.toString()
                        println("url1 : ${url1}")
                        if(i==max-1){

                            profileList.add(Dimgs(url1, url1, url1))

                        }

                    }
                    1->{url2=uri.toString()
                        println("url2 : ${url2}")
                        if(i==max-1){
                            println("add!")

                            profileList.add(Dimgs(url1, url2, url2))

                        }
                    }
                    2->{url3=uri.toString()
                        println("url3 : ${url3}")

                        profileList.add(Dimgs(url1, url2, url3))

                    }
                    else->""
                }
                println("i==?${i}")


                  //  profileList.add(Dimgs(url1,url2,url3))


            }
           Tasks.await(asy)// 이거 wait 하는건가!?
            if(i==max-1){
                println("add!")
                dimglistrv.setHasFixedSize(true)
                val mLayoutManager =  LinearLayoutManager(this);
                dimglistrv.layoutManager = mLayoutManager;
                dimglistrv.adapter=dimgAdapter(this,profileList)
            }

        }


        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?" +
                "alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
var b=""
       // profileList.add(Dimgs(a,a,a))

       // profileList.add(Dimgs(b,b,b))

        //profileList.add(Dimgs(a,a,a))
       // dimglistrv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)





        println("for ${profileList.size} ")
        for(pro in profileList){
            println("list : ${pro.dimg1}, ${pro.dimg2}, ${pro.dimg3}")
        }



        println("read end")

    }
}
