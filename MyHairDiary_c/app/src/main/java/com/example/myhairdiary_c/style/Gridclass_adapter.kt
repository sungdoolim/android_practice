package com.example.myhairdiary_c.style

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.detailedDesigner
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.main.detailedRecommend

class MyAdapter(    context: Context,    layout: Int,    userDTO:ArrayList<photourl>) :
    BaseAdapter() {
    var context: Context
    var layout: Int
    var userDTO:ArrayList<photourl>
    var inf: LayoutInflater
    override fun getCount(): Int {
        return userDTO.size
    }
    override fun getItem(position: Int): Any {
        // 눌렀을때 이벤트 처리하는 곳 근데 이벤트 발생 메서드를 호출해야함 // setitemonclicklister에서 getitem를 호출하고 있어야 함
        val pref=context.getSharedPreferences("recommended",0)
        val edit=pref.edit()
        edit.putString("name",userDTO[position].name)
        edit.putString("did",userDTO[position].id)
        edit.putString("gender",userDTO[position].gender)
        edit.putString("url",userDTO[position].url)
        edit.putString("customid",userDTO[position].customid)
        edit.putString("length",userDTO[position].length)
        edit.putString("memo",userDTO[position].memo)
        edit.putInt("pcount",userDTO[position].pcount)
        edit.putString("style",userDTO[position].style)
        edit.apply()
        val intent = Intent(context, detailedRecommend::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 이거 없으면 인텐트 불가
        context.startActivity(intent)

        Toast.makeText(context,"toast : ${position}",Toast.LENGTH_SHORT).show()
        return userDTO[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getView(        position: Int,        convertView: View?,        parent: ViewGroup?    ): View? {
        var convertView = convertView
        if (convertView == null) convertView = inf.inflate(layout, null)
//            val iv =
//                convertView!!.findViewById<View>(R.id.imV) as ImageView
        //   iv.setImageResource(img[position])
        val iv=convertView!!.findViewById<ImageView>(R.id.search_imageurl)
        val tv=convertView!!.findViewById<TextView>(R.id.search_desc)
        tv.text=userDTO[position].name
        Glide.with(context).load(userDTO[position].url).into(iv)
        // iv.setImageResource(imgurl[position])
        return convertView
    }
    init {
        this.context = context
        this.layout = layout
        this.userDTO=userDTO
        inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}