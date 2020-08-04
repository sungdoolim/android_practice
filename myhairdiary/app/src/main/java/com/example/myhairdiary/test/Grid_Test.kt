package com.example.myhairdiary.test

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myhairdiary.R
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_grid__test.*


class Grid_Test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid__test)
        val pref=getSharedPreferences("ins",0)

         val img = intArrayOf(
            R.drawable.babyele,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_foreground

        )
        selectList("1234",5)



    }

    internal class MyAdapter(
        context: Context,
        layout: Int,
        imgurl: ArrayList<String>,
        tvurl: ArrayList<String>
    ) : BaseAdapter() {
        var context: Context
        var layout: Int
        var imgurl: ArrayList<String>
        var tvurl: ArrayList<String>
        var inf: LayoutInflater


        override fun getCount(): Int {
            return imgurl.size
        }

        override fun getItem(position: Int): Any {
            return imgurl[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View? {
            var convertView = convertView
            if (convertView == null) convertView = inf.inflate(layout, null)
//            val iv =
//                convertView!!.findViewById<View>(R.id.imV) as ImageView
            //   iv.setImageResource(img[position])
            val iv=convertView!!.findViewById<ImageView>(R.id.imV)
            val tv=convertView!!.findViewById<TextView>(R.id.tv)
            tv.text=tvurl[position].toString()
            Glide.with(context).load(imgurl[position]).into(iv)
            // iv.setImageResource(imgurl[position])
            return convertView
        }

        init {
            this.context = context
            this.layout = layout
            this.imgurl = imgurl
            this.tvurl = tvurl
            inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }


    }
    public fun selectList(id:String, index:Int) {

        var imgurl=ArrayList<String>()
        var tvurl=ArrayList<String>()
        var kount=0
        for(i in 0..index-1){

            println("i : ${i}")
            var storageRef = FirebaseStorage.getInstance().reference.child("images").child(id)
                .child(i.toString())
                var k=selec(kount,imgurl,tvurl,storageRef,index)
//            Tasks.await(k)
            println("k : ${kount}")
            kount++


        }



    }
    public fun selec(kount:Int,imgurl:ArrayList<String>,tvurl:ArrayList<String>,storageRef:StorageReference,index:Int): Task<Uri> {



        return storageRef.downloadUrl.addOnSuccessListener { uri ->
            //loadPhoto(uri.toString(),i)
            imgurl.add(uri.toString())
            tvurl.add(uri.toString())
           // println("\n${i}:${kount}:${uri.toString()}")
println("kount :  ${kount}  i:  ${index} ")
            if(kount==index-1) {
                print("들어가자 : ${imgurl.size}")
                var adapter = MyAdapter(
                    applicationContext,
                    R.layout.grid_image,  // GridView 항목의 레이아웃 row.xml
                    imgurl,tvurl
                ) // 데이터

                gridView1.adapter = adapter
                gridView1.setOnItemClickListener { parent, view, position, id ->
                    println("position : ${position}")
                    //    println("info : ${}")
                }
            }
        }


    }

}
