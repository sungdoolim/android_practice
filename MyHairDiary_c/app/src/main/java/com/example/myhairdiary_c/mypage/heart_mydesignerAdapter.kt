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
import com.example.myhairdiary_c.designers.detailedDesigner

class heart_mydesignerAdapter (val context: Context, val designerList:ArrayList<designer>): RecyclerView.Adapter<heart_mydesignerAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.heart_mydesigner_adapter,parent,false)
        // recyclerview 어뎁터

        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                //클릭시 동작
                val curPos:Int=adapterPosition
                var dl: designer =designerList.get(curPos)
                val pref=context.getSharedPreferences("selected",0)
                val edit=pref.edit()
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
                val intent = Intent(view.getContext(), detailedDesigner::class.java)
                context.startActivity(intent)
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