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
import com.example.myhairdiary_c.designers.designer

class heart_mydesignerAdapter (val context: Context, val designerList:ArrayList<designer>): RecyclerView.Adapter<heart_mydesignerAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.heart_mydesigner_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: designer =designerList.get(curPos)
                val prefrecommend=context.getSharedPreferences("recommended",0)
                val edit=prefrecommend.edit()
                edit.putString("did",dl.id)
                edit.putString("memo",dl.memo)
                edit.putString("name",dl.name)

                edit.apply()

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





        Glide.with(context).load(designerList.get(position).profile).into(holder.mydesigner)


    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val mydesigner=itemView.findViewById<ImageView>(R.id.heart_mydesigner)
        val heart=itemView.findViewById<ImageView>(R.id.heart_mydesigner_heart)


    }





}