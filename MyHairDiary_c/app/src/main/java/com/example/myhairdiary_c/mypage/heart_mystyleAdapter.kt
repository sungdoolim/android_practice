package com.example.myhairdiary_c.mypage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl
import com.example.myhairdiary_c.main.detailedRecommend

class heart_mystyleAdapter (val context: Context, val designerList:ArrayList<photourl>): RecyclerView.Adapter<heart_mystyleAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.heart_mystyle_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {

                val curPos:Int=adapterPosition
                var dl: photourl =designerList.get(curPos)
                val prefrecommend=context.getSharedPreferences("recommended",0)
                val edit=prefrecommend.edit()
                edit.putString("url",dl.url)
                edit.putString("gender",dl.gender)
                edit.putString("did",dl.id)
                edit.putString("length",dl.length)
                edit.putString("memo",dl.memo)
                edit.putString("name",dl.name)
                edit.putString("style",dl.style)
                edit.putInt("pcount",dl.pcount)
                edit.apply()

                var intent= Intent(view.getContext(), detailedRecommend::class.java)// 이게 공지나...그런 설정들
                context.startActivity(intent)

//                println("${dl.url}")
//                println("${dl.gender}")
//                println("${dl.id}")
//                println("${dl.name}")

                // 정보 다 받을수 있음


            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // holder.memo.setImageResource(1)

        Glide.with(context).load(designerList.get(position).url).into(holder.mystyle)


    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val mystyle=itemView.findViewById<ImageView>(R.id.heart_mystyle)
        val heart=itemView.findViewById<ImageView>(R.id.heart_mystyle_heart)


    }





}