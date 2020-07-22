package com.example.myhairdiary.designers

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhairdiary.R
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_dimg_adpt_list.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

interface  designer_img_inter {
   companion object{
       val designer_imgL = ArrayList<Dimgs>()
       public fun selectList_interface(firestore: FirebaseFirestore,context:Context,itemView: View){

               selectList(firestore, context, itemView)
           }
       public fun selectList(firestore: FirebaseFirestore,context:Context,itemView: View) {
           //(itemView: View): RecyclerView.ViewHolder(itemView)
           println("read")
           val pref=context.getSharedPreferences("ins",0)
           var sesid=pref.getString("id","null")
           var max: Int = Integer.parseInt(pref.getString("index","0")!!)

           val profileList=ArrayList<Dimgs>()
           //    Dimgs(a,b,a),Dimgs(b,a,b),Dimgs(a,b,a)

           var url1=""
           var url2=""
           var url3=""



           for(i in 0..max-1) {
               var storageRef = FirebaseStorage.getInstance().reference.child("images")
                   .child(sesid + "_." + i.toString())
               println(i)
               //   println("dwurl : ${storageRef.downloadUrl}")




                       var asy = storageRef.downloadUrl.addOnSuccessListener { uri ->
                           //      println("dwrul2 : ${uri}")
                           //   loadPhoto(uri.toString(),i)
                           println("i:${i}, max : ${max}")
                           when ((i) % 3) {
                               0 -> {
                                   url1 = uri.toString()
                                   println("url1 : ${url1}")
                                   if (i == max - 1) {
                                       profileList.add(Dimgs(url1, url1, url1))
                                   }
                               }
                               1 -> {
                                   url2 = uri.toString()
                                   println("url2 : ${url2}")
                                   if (i == max - 1) {
                                       println("add!")
                                       profileList.add(Dimgs(url1, url2, url2))
                                   }
                               }
                               2 -> {
                                   url3 = uri.toString()
                                   println("url3 : ${url3}")
                                   profileList.add(Dimgs(url1, url2, url3))
                               }
                               else -> ""
                           }
                           println("i==?${i}")


                           //  profileList.add(Dimgs(url1,url2,url3))


                       }

               //block
               if(i==max-1){
                   println("add!")
                   // val a= findViewById(R.id.dimglistrv)
                   val a=itemView.findViewById<RecyclerView>(R.id.dimglistrv)
                   a.setHasFixedSize(true)
                   val mLayoutManager =  LinearLayoutManager(context);
                   a.layoutManager = mLayoutManager;
                   a.adapter=dimgAdapter(context,profileList)

               }
             // Tasks.await(asy)// 이거 wait 하는건가!?
           }//for



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

}