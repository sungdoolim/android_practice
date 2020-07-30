package com.example.myhairdiary.designers

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.VideoView
import android.widget.MediaController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.ReView.Review
import com.example.myhairdiary.designers.test.dimg_adpt_list
import com.example.myhairdiary.designers.trace.Tracking
import com.example.myhairdiary.designers.trace.TrackingMemo
import com.example.myhairdiary.social.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import java.io.File

class detaildesigner : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detaildesigner)
        val age=intent.getIntExtra("age",0)
        val name=intent.getStringExtra("name")?:"null"
        val year=intent.getIntExtra("year",0)
        val memo=intent.getStringExtra("memo")?:"null"
        val phone=intent.getStringExtra("phone")?:"null"
        val index : Int=Integer.parseInt(intent.getStringExtra("index")?:"0")
        val reviewcount=intent.getIntExtra("reviewcount",-1)
        print("index : ! ${index}")
        val did=intent.getStringExtra("did")?:"null"
        println(age)
        println(name)
        println(year)
        println(memo)
        detailage.text=age.toString();
        datailname.text=name;
        detailyear.text=year.toString();
        detailmemo.text=memo;
        detailid.text=did;
        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore,did,index)// 이미지뷰 띄우기
        selectList2(firestore)
       // selectList2(firestore)
// 얘는 원래 recycler뷰를 적용하려했는데...


        gotokakao.setOnClickListener(){
//            val intent = Intent(this, Openkakao::class.java)
//            intent.putExtra("did",did)
//            startActivity(intent)
            social(did,1)
        }
        gotonaver.setOnClickListener(){
            social(did,2)
        }
        gotoface.setOnClickListener(){
            social(did,3)
        }
        gotoyoutube.setOnClickListener(){
            social(did,4)
        }
        gotoinsta.setOnClickListener(){
            social(did,5)
        }
        adpt_list.setOnClickListener(){
            val intent = Intent(this, dimg_adpt_list::class.java)
            intent.putExtra("did",did)
            intent.putExtra("index",index)
            startActivity(intent)
        }
        tracephoto.setOnClickListener(){
            val intent = Intent(this, Tracking::class.java)
            intent.putExtra("did",did)
            intent.putExtra("index",index.toString())
            startActivity(intent)
        }
        tracememo.setOnClickListener(){
            val intent = Intent(this, TrackingMemo::class.java)
            intent.putExtra("did",did)
          //  intent.putExtra("index",index.toString())
            startActivity(intent)
        }
        review.setOnClickListener(){
            val intent = Intent(this, Review::class.java)
            intent.putExtra("did",did)
            intent.putExtra("reviewcount",reviewcount)
            //  intent.putExtra("index",index.toString())
            startActivity(intent)
        }

    }
    public fun social(id:String,num:Int){
        var firestore = FirebaseFirestore.getInstance()
        firestore?.collection("hair_diary").whereEqualTo("id",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        var userDTO =dc.toObject(designer::class.java)
                        println("success ${userDTO.toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        when(num){
                            1-> intent =  Intent(Intent.ACTION_VIEW, Uri.parse(userDTO!!.openkakaourl));
                            2-> intent =  Intent(Intent.ACTION_VIEW, Uri.parse(userDTO!!.naverurl));
                            3-> intent =  Intent(Intent.ACTION_VIEW, Uri.parse(userDTO!!.faceurl));
                            4-> intent =  Intent(Intent.ACTION_VIEW, Uri.parse(userDTO!!.youurl));
                            else-> intent =  Intent(Intent.ACTION_VIEW, Uri.parse(userDTO!!.instaurl));
                        }
                        startActivity(intent);
                    }
                }else{
                    println("fail")
                }
            }
    }

    fun loadPhoto(downloadUrl : String,i :Int) {
        when(i){
            0->  Glide.with(this).load(downloadUrl).into(testimgview0)
            1->  Glide.with(this).load(downloadUrl).into(testimgview1)
            2-> {
                var mediaController= MediaController(this)
                var v=findViewById<VideoView>(R.id.testimgview2)
             //   mediaController.setAnchorView(testimgview2)
                v.setMediaController(mediaController)
                var url=Uri.parse(downloadUrl+".mp4")
                println("url : ${url}")
                v.setVideoURI(url)
                v.requestFocus()
                v.start()





             //   Glide.with(this).load(Uri.fromFile(File(downloadUrl))).into(testimgview2)

            }
            3->  Glide.with(this).load(downloadUrl).into(testimgview3)
            4->  Glide.with(this).load(downloadUrl).into(testimgview4)
            //   Glide.with(this).load(downloadUrl).into(DrawableImageViewTarget(testimgview4))
            5->  Glide.with(this).load(downloadUrl).into(testimgview5)
            6->  Glide.with(this).load(downloadUrl).into(testimgview6)
            7->  Glide.with(this).load(downloadUrl).into(testimgview7)
            8->  Glide.with(this).load(downloadUrl).into(testimgview8)
            9->  Glide.with(this).load(downloadUrl).into(testimgview4)
            10->  Glide.with(this).load(downloadUrl).into(testimgview5)
            11->  Glide.with(this).load(downloadUrl).into(testimgview6)
            12->  Glide.with(this).load(downloadUrl).into(testimgview7)
            13->  Glide.with(this).load(downloadUrl).into(testimgview8)
            14->  Glide.with(this).load(downloadUrl).into(testimgview7)
            15->  Glide.with(this).load(downloadUrl).into(testimgview8)
            else->""
        }
    }
    public fun selectList2(firestore:FirebaseFirestore) {
        println("read")
        var sesid=intent.getStringExtra("did")
        var max: Int = intent.getIntExtra("index",0)
        val profileList=ArrayList<Dimgs>()
        //    Dimgs(a,b,a),Dimgs(b,a,b),Dimgs(a,b,a)
        var url1=""
        var url2=""
        var url3=""
        var ard=ArrayList<String>()
        var kount=0
        for(i in 0..max-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images")
                .child(sesid).child(i.toString())
            println({"i : ${i},max : ${max}"})

            storageRef.downloadUrl.addOnSuccessListener { uri ->
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
                        ard.clear()
                        //  profileList.add(Dimgs(ard[0],ard[1],ard[2]))
                    }
                    detail_rv.setHasFixedSize(true)
                    val mLayoutManager =  LinearLayoutManager(this);
                    detail_rv.layoutManager = mLayoutManager;
                    detail_rv.adapter=
                        dimgAdapter(this, profileList)
                }
                kount++
            }
        }
        if((max-1)/3!=2){
            profileList.add(Dimgs(url1,url2,url3))
        }
        var a="https://firebasestorage.googleapis.com/v0/b/flut-template.appspot.com/o/images%2F1234_.0?" +
                "alt=media&token=ab8a984f-0441-4d1b-92d9-980d514f3568"
      //  profileList.add(Dimgs(a,b,c))
//        designerimg_rv.setHasFixedSize(true)
//        val mLayoutManager =  LinearLayoutManager(this);
//        designerimg_rv.layoutManager = mLayoutManager;
//        designerimg_rv.adapter=dimgAdapter(this,profileList)
       // println("read end")
    }
    public fun selectList(firestore:FirebaseFirestore,id:String,index:Int) {
        val pref=getSharedPreferences("ins",0)
            for(i in 0..index-1){
            var storageRef = FirebaseStorage.getInstance().reference.child("images").child(id)
                .child(i.toString())

            storageRef.downloadUrl.addOnSuccessListener { uri ->

                loadPhoto(uri.toString(),i)
            }
        }
    }
}
