package com.example.myhairdiary_c.style

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl

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
        return userDTO[position]
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