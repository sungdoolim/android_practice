package com.example.myhairdiary_c.main

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


class recommend_trend_adapter (val context: Context, val designerList:ArrayList<photourl>,val whatodo:Int): RecyclerView.Adapter<recommend_trend_adapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.search_grid_adapter,parent,false)

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
                edit.putInt("like",dl.like)
                edit.putString("memo",dl.memo)
                edit.putString("name",dl.name)
                edit.putString("style",dl.style)
                edit.putInt("pcount",dl.pcount)
                edit.apply()

//                println("${dl.url}")
//                println("${dl.gender}")
//                println("${dl.id}")
//                println("${dl.name}")

                // 정보 다 받을수 있음

                when(whatodo){
                    1->{
                        val intent = Intent(view.getContext(), detailedTrend::class.java)
                        context.startActivity(intent)
                    }
                    2->{

                }

                    else->{
                        val intent = Intent(view.getContext(), detailedRecommend::class.java)
                        context.startActivity(intent)
                    }
                }//when

            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // holder.memo.setImageResource(1)



        holder.desc.text=designerList.get(position).style
        if(designerList.get(position).url==""){

            holder.dimg.setImageResource(R.drawable.trend_text)//designerList.get(position).dimg
        }else {
            Glide.with(context).load(designerList.get(position).url).into(holder.dimg)
        }

    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val dimg=itemView.findViewById<ImageView>(R.id.search_imageurl)
        val desc=itemView.findViewById<TextView>(R.id.search_desc)


    }





}