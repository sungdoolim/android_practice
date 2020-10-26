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
import android.graphics.Color as Color


class coupon_adapter(val context: Context?, val designerList:ArrayList<list_data>): RecyclerView.Adapter<coupon_adapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.couponadapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: list_data =designerList.get(curPos)

                val intent = Intent(view.getContext(), detail_coupon::class.java)
                val pref=context!!.getSharedPreferences("selected_coupon",0)

                val edit=pref.edit()

                edit.clear()
                edit.putString("url",dl.url)
                edit.putString("due",dl.due)
                edit.putInt("rest",dl.rest)
                edit.putString("content",dl.content)
                edit.putString("id",dl.id)
                edit.putInt("index",dl.index)
                edit.putBoolean("ischecked",dl.ischecked)
                edit.putString("place",dl.place)
                edit.apply()



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



        holder.due.text=designerList.get(position).due
        holder.rest.text= designerList.get(position).rest.toString()
        holder.index.text=designerList.get(position).index.toString()
        if (context != null) {
            Glide.with(context).load(designerList.get(position).url).into(holder.img)
        }



    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val layout=itemView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.lay)
        val due=itemView.findViewById<TextView>(R.id.due)
        val rest=itemView.findViewById<TextView>(R.id.rest)
        val index=itemView.findViewById<TextView>(R.id.index)
        val img=itemView.findViewById<ImageView>(R.id.imageView)



    }





}