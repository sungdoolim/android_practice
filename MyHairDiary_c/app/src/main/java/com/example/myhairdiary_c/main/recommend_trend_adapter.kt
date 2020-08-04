package com.example.myhairdiary_c.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.photourl


class recommend_trend_adapter (val context: Context, val designerList:ArrayList<photourl>): RecyclerView.Adapter<recommend_trend_adapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.search_grid_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: photourl =designerList.get(curPos)

                println("${dl.url}")
                println("${dl.gender}")
                println("${dl.id}")
                println("${dl.name}")
                // 정보 다 받을수 있음

            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // holder.memo.setImageResource(1)



        holder.dimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg

        Glide.with(context).load(designerList.get(position).url).into(holder.dimg)


    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val dimg=itemView.findViewById<ImageView>(R.id.search_imageurl)


    }





}