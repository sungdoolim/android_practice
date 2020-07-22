package com.example.myhairdiary.calendar

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhairdiary.R


class Cal_adapter (val context: Context, val designerList:ArrayList<calist>): RecyclerView.Adapter<Cal_adapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.cal_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: calist =designerList.get(curPos)
//                val intent = Intent(view.getContext(), detaildesigner::class.java)
//                intent.putExtra("id",dl.id)
//                intent.putExtra("age",dl.age)
//                intent.putExtra("memo",dl.memo)
//                intent.putExtra("name",dl.name)
//                intent.putExtra("phone",dl.phone)
//                intent.putExtra("index",dl.index.toString())
//                intent.putExtra("year",dl.year)
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
        holder.cus.text=designerList.get(position).customer
        holder.id.text=designerList.get(position).id
        holder.date.text=designerList.get(position).date.toString()
        holder.time.text=designerList.get(position).time
        holder.memo.text=designerList.get(position).memo
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val cus=itemView.findViewById<TextView>(R.id.cus)
        val id=itemView.findViewById<TextView>(R.id.id)
        val date=itemView.findViewById<TextView>(R.id.date)
        val memo=itemView.findViewById<TextView>(R.id.memo)
        val time=itemView.findViewById<TextView>(R.id.time)
    }
}