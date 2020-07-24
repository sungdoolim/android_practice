package com.example.myhairdiary.designers.trace
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhairdiary.R

class tracAdapter (val context:Context, val designerList:ArrayList<feedData>): RecyclerView.Adapter<tracAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.trackingadapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: feedData =designerList.get(curPos)

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

        holder.tracmemo.text=designerList.get(position).memo
        holder.fire_time.text= designerList.get(position).timestamp.toDate().toString()
        holder.tracimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        val tracimg=itemView.findViewById<ImageView>(R.id.tracimg)
        val fire_time=itemView.findViewById<TextView>(R.id.fire_time)
        val tracmemo=itemView.findViewById<TextView>(R.id.tracmemo)


    }


}