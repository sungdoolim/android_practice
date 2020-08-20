package com.example.lovelist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.graphics.Color as Color


class list_adapter(val context: Context?, val designerList:ArrayList<list_data>): RecyclerView.Adapter<list_adapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.listadapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: list_data =designerList.get(curPos)

                val intent = Intent(view.getContext(), detail_list::class.java)
                val pref=context!!.getSharedPreferences("selected",0)

                val edit=pref.edit()

                edit.clear()
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


        var id=designerList.get(position).id
        holder.place.text=  "장소는!? : "+designerList.get(position).place
        holder.content.text="뭐 할까 우리??!? : "+ designerList.get(position).content
        if(id=="누꿍"){
            holder.layout.setBackgroundColor(Color.parseColor("#F1ECD2"))
        }else{
            holder.layout.setBackgroundColor(Color.parseColor("#E6F8FA"))
        }
        if(designerList.get(position).ischecked){
            holder.checked.setImageResource(R.drawable.checked_icon)
        }else{
            holder.checked.setImageResource(R.drawable.checkednot_icon)
        }
        holder.id.text=designerList.get(position).id


    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val layout=itemView.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.lay)
        val place=itemView.findViewById<TextView>(R.id.place)
        val content=itemView.findViewById<TextView>(R.id.content)
        val id=itemView.findViewById<TextView>(R.id.oursesid)
        val checked=itemView.findViewById<ImageView>(R.id.checked_img)


    }





}