package com.example.myhairdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class designerAdapter (val designerList:ArrayList<designer>): RecyclerView.Adapter<designerAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): designerAdapter.CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.designer_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl:designer=designerList.get(curPos)
              //  Toast.makeText(parent.context,"이름 :${profile.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: designerAdapter.CustomViewHolder, position: Int) {
       // holder.memo.setImageResource(1)

        holder.name.text=designerList.get(position).name
        holder.age.text=designerList.get(position).age.toString()
        holder.year.text=designerList.get(position).year.toString()
        holder.phone.text=designerList.get(position).phone
        holder.memo.text=designerList.get(position).memo
        holder.dimg.setImageResource(designerList.get(position).dimg)
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dimg=itemView.findViewById<ImageView>(R.id.Dimg)
        val memo=itemView.findViewById<TextView>(R.id.Dmemo)
        val name=itemView.findViewById<TextView>(R.id.Dname)
        val age=itemView.findViewById<TextView>(R.id.Dage)
        val phone=itemView.findViewById<TextView>(R.id.Dphone)
        val year=itemView.findViewById<TextView>(R.id.Dyear)

    }


}