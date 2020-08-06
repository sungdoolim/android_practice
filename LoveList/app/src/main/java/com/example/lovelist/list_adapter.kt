package com.example.lovelist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class list_adapter (val context: Context, val designerList:ArrayList<list_data>): RecyclerView.Adapter<list_adapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.listadapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: list_data =designerList.get(curPos)

              //  val intent = Intent(view.getContext(), detailedDesigner::class.java)
                val pref=context.getSharedPreferences("selected",0)
                val edit=pref.edit()
                edit.clear()

                //context.startActivity(intent)

                //  Toast.makeText(parent.context,"이름 :${profile.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return designerList.size
    }
    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // holder.memo.setImageResource(1)

        holder.title.text=designerList.get(position).title
        holder.content.text=designerList.get(position).content



    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title=itemView.findViewById<TextView>(R.id.title)
        val content=itemView.findViewById<TextView>(R.id.content)


    }





}