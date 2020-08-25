package com.example.myhairdiary_c.style

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.main.detailedRecommend


// recyclerview 가 아닌 gridview의 어뎁터 입니다.
class grid_adapter(context: Context, layout: Int, userDTO:ArrayList<designer>) :
    BaseAdapter() {
    var context: Context
    var layout: Int
    var userDTO:ArrayList<designer>
    var inf: LayoutInflater
    override fun getCount(): Int {
        return userDTO.size
    }
    override fun getItem(position: Int): Any {
        // 선택됬을때 여기서 처리합니다.
        val pref=context.getSharedPreferences("selected",0)
        val edit=pref.edit()
        val dl=userDTO[position]
        edit.clear()
        edit.putString("did",dl.id)
        edit.putInt("age",dl.age)
        edit.putString("memo",dl.memo)
        edit.putString("name",dl.name)
        edit.putString("phone",dl.phone)
        edit.putInt("index",dl.index)
        edit.putInt("year",dl.year)
        edit.putInt("monthc",dl.monthc)
        edit.putString("major",dl.major)
        edit.putInt("reviewcount",dl.reviewcount)
        edit.putString("profile",dl.profile)
        edit.apply()
        val intent = Intent(context, detailedRecommend::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // 이거 없으면 인텐트 불가
        context.startActivity(intent)
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
        if (convertView == null) {convertView = inf.inflate(layout, null)}

        val iv=convertView!!.findViewById<ImageView>(R.id.search_imageurl)
        Glide.with(context).load(userDTO[position].profile).into(iv)
        return convertView
    }
    init {
        this.context = context
        this.layout = layout
        this.userDTO=userDTO
        inf = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}