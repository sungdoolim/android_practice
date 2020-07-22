package com.example.myhairdiary.designers

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhairdiary.R

class designerAdapter (val context:Context, val designerList:ArrayList<designer>): RecyclerView.Adapter<designerAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.designer_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: designer =designerList.get(curPos)

                val intent = Intent(view.getContext(), detaildesigner::class.java)
                intent.putExtra("id",dl.id)
                intent.putExtra("age",dl.age)
                intent.putExtra("memo",dl.memo)
                intent.putExtra("name",dl.name)
                intent.putExtra("phone",dl.phone)
                intent.putExtra("index",dl.index.toString())
                intent.putExtra("year",dl.year)
                context.startActivity(intent)

              //  Toast.makeText(parent.context,"이름 :${profile.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
       // holder.memo.setImageResource(1)

        holder.name.text=designerList.get(position).name
        holder.did.text=designerList.get(position).id
        holder.age.text=designerList.get(position).age.toString()
        holder.year.text=designerList.get(position).year.toString()
        holder.phone.text=designerList.get(position).phone
        holder.memo.text=designerList.get(position).memo

        holder.dimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val did=itemView.findViewById<TextView>(R.id.Did)
        val dimg=itemView.findViewById<ImageView>(R.id.Dimg)
        val memo=itemView.findViewById<TextView>(R.id.Dmemo)
        val name=itemView.findViewById<TextView>(R.id.Dname)
        val age=itemView.findViewById<TextView>(R.id.Dage)
        val phone=itemView.findViewById<TextView>(R.id.Dphone)
        val year=itemView.findViewById<TextView>(R.id.Dyear)


    }


}