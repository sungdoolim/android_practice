package com.example.myhairdiary.designers.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.Dimgs
import com.example.myhairdiary.designers.dimgAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_dimg_adpt_list.*

class dimg_adpt_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dimg_adpt_list)
        var firestore = FirebaseFirestore.getInstance()
      //  designer_img_inter.selectList_interface(firestore,this, View(this))
        val profileList=ArrayList<Dimgs>()
        selectList(firestore,profileList)
        //selectList2(firestore,profileList)



    }

    public fun selectList2(firestore:FirebaseFirestore,profileList: ArrayList<Dimgs2>) {
        println("read")
        var sesid=intent.getStringExtra("id")
        var max: Int = intent.getIntExtra("index",0)
        println("max : ${max}")
        var kount=0

        for(i in 0..max-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images").child(sesid.toString())
            println("세션 : ${sesid} : ${i}")


            println(i)
            var  asy= storageRef.downloadUrl.addOnSuccessListener {
             //   if(it.)
                println("uri ${it.toString()}")
              profileList.add(Dimgs2(it.toString()))
                println("${i} : ${profileList.size}")

                if(kount==max-1){
                    dimglistrv.setHasFixedSize(true)
                    val mLayoutManager =  LinearLayoutManager(this);
                    dimglistrv.layoutManager = mLayoutManager;
                    dimglistrv.adapter=
                        dimgAdapter2(this, profileList)
                }
                kount++
            }


//            Tasks.await(asy)
        }

//        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?" +
//                "alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
//        profileList.add(Dimgs2(a.toString()))
//        println("for ${profileList.size} ")
//        dimglistrv.setHasFixedSize(true)
//        val mLayoutManager =  LinearLayoutManager(this);
//        dimglistrv.layoutManager = mLayoutManager;
//        dimglistrv.adapter=
//            dimgAdapter2(this, profileList)
    }


    public fun selectList(firestore:FirebaseFirestore,profileList: ArrayList<Dimgs>) {
        println("read")
       // val pref=getSharedPreferences("ins",0)
     //   var sesid=pref.getString("id","null")// 이거 뭐여
        var sesid=intent.getStringExtra("did")
        var max: Int = intent.getIntExtra("index",0)
      //  val profileList=ArrayList<Dimgs>()
        var url1=""
        var url2=""
        var url3=""
        // Dimgs(a,b,a),Dimgs(b,a,b),Dimgs(a,b,a)
        var dimgar=ArrayList<String>()
println("max : ${max}")
        var ard=ArrayList<String>()
        var kount=0
        for(i in 0..max-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images")
                .child(sesid.toString()).child(""+i.toString())
            println("세션 : ${sesid} : ${i}")

            println(i)
            var  asy= storageRef.downloadUrl.addOnSuccessListener { uri ->
               //profileList.add(Dimgs(uri.toString()))

                ard.add(uri.toString())
                if(ard.size==3){
                    profileList.add(Dimgs(ard[0],ard[1],ard[2]))
                    ard.clear()
                }
                println("${i} : ${profileList.size}")
                if(kount==max-1){
                    if(ard.size!=0){
                        var k=ard.size
                        when(k){
                            1-> profileList.add(Dimgs(ard[0],ard[0],ard[0]))
                            2-> profileList.add(Dimgs(ard[0],ard[1],ard[1]))
                            else->""
                        }
                     //  profileList.add(Dimgs(ard[0],ard[1],ard[2]))
                    }
                    dimglistrv.setHasFixedSize(true)
                    val mLayoutManager =  LinearLayoutManager(this);
                    dimglistrv.layoutManager = mLayoutManager;
                    dimglistrv.adapter=
                        dimgAdapter(this, profileList)
                }
                kount++
            }


//            Tasks.await(asy)
        }

//        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?" +
//                "alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
//        profileList.add(Dimgs2(a.toString()))
//        println("for ${profileList.size} ")
//        dimglistrv.setHasFixedSize(true)
//        val mLayoutManager =  LinearLayoutManager(this);
//        dimglistrv.layoutManager = mLayoutManager;
//        dimglistrv.adapter=
//            dimgAdapter2(this, profileList)
    }
    fun k (sesid:String="",i:Int=0,max:Int=0,url1:String="",url2:String="",url3:String="",profileList:ArrayList<Dimgs>){
        var url11=url1
        var url22=url2
        var url33=url3

        val profileList=ArrayList<Dimgs>()
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
            .child(sesid).child(i.toString())
        println(i)
        //   println("dwurl : ${storageRef.downloadUrl}")
        var asy= storageRef.downloadUrl.addOnSuccessListener { uri ->
            println("i:${i}, max : ${max}")
            when((i)%3){
                0->{url11=uri.toString()
                    println("url1 : ${url1}")
                    if(i==max-1){
                      //  profileList.add(Dimgs(url1, url1, url1))
                    }
                }
                1->{url22=uri.toString()
                    println("url2 : ${url2}")
                    if(i==max-1){
                        println("add!")
                      //  profileList.add(Dimgs(url1, url2, url2))
                    }
                }
                2->{url33=uri.toString()
                    println("url3 : ${url3}")
                  //  profileList.add(Dimgs(url1, url2, url3))
                }
                else->""
            }
            println("i==?${i}")
        }
        //Tasks.await(asy)// 이거 wait 하는건가!?
        if(i==max-1){
            println("add!")
            dimglistrv.setHasFixedSize(true)
            val mLayoutManager =  LinearLayoutManager(this);
            dimglistrv.layoutManager = mLayoutManager;
            dimglistrv.adapter=
                dimgAdapter(this, profileList)
        }
    }
}
