package com.example.myhairdiary.designers.ReView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhairdiary.R
import com.google.firebase.storage.FirebaseStorage

class reviewAdapter (val context:Context, val designerList:ArrayList<review_data>): RecyclerView.Adapter<reviewAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.review_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: review_data =designerList.get(curPos)

//                val intent = Intent(view.getContext(), detaildesigner::class.java)
//                intent.putExtra("id",dl.id)
//                intent.putExtra("age",dl.age)
//                intent.putExtra("memo",dl.memo)
//                intent.putExtra("name",dl.name)
//                intent.putExtra("phone",dl.phone)
//                intent.putExtra("index",dl.index.toString())
//                intent.putExtra("year",dl.year)
//                intent.putExtra("monthc",dl.monthc)
//                intent.putExtra("major",dl.major)
//
//                context.startActivity(intent)

                //  Toast.makeText(parent.context,"이름 :${profile.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // holder.memo.setImageResource(1)

        holder.customid.text=designerList.get(position).customid
        holder.designer_id.text=designerList.get(position).designer_id
        holder.timestamp.text=designerList.get(position).timestamp.toString()
        holder.memo.text=designerList.get(position).memo

        holder.drvimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg

     //   Glide.with(context).load(dimglist.get(position).dimg1).into(holder.Dimg1)

   //     holder.drvimg
        Glide.with(context).load(designerList.get(position).drimgurl).into(holder.drvimg)


    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val drvimg=itemView.findViewById<ImageView>(R.id.RVimg)
        val customid=itemView.findViewById<TextView>(R.id.RVcid)
        val designer_id=itemView.findViewById<TextView>(R.id.RVid)
        val memo=itemView.findViewById<TextView>(R.id.RVmemo)
        val timestamp=itemView.findViewById<TextView>(R.id.RVtimestamp)

    }





}