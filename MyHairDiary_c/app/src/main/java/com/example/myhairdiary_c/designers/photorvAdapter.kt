package com.example.myhairdiary_c.designers

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

//designerAdapter 와 로직이 같습니다.

class photorvAdapter (val context: Context, val designerList:ArrayList<photourl>): RecyclerView.Adapter<photorvAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.photorv_adapter,parent,false)
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: photourl =designerList.get(curPos)
            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.name.text=designerList.get(position).name
        holder.did.text=designerList.get(position).id
        holder.memo.text=designerList.get(position).memo
        holder.dimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg
        Glide.with(context).load(designerList.get(position).url).into(holder.dimg)
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val did=itemView.findViewById<TextView>(R.id.Did)
        val dimg=itemView.findViewById<ImageView>(R.id.Dimg)
        val memo=itemView.findViewById<TextView>(R.id.Dmemo)
        val name=itemView.findViewById<TextView>(R.id.Dname)
        val age=itemView.findViewById<TextView>(R.id.Dage)
        val phone=itemView.findViewById<TextView>(R.id.Dphone)
        val year=itemView.findViewById<TextView>(R.id.Dyear)
        val major=itemView.findViewById<TextView>(R.id.Dmajor)
        val monthc=itemView.findViewById<TextView>(R.id.Dmonthc)
    }





}