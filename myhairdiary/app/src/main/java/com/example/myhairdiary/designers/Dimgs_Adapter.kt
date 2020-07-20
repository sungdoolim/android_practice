package com.example.myhairdiary.designers

import com.example.myhairdiary.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detaildesigner.*

class dimgAdapter( val dimglist: ArrayList<Dimgs>): RecyclerView.Adapter<dimgAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dimgAdapter.CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.designerimgs_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                Toast.makeText(parent.context,"이름 :${curPos}", Toast.LENGTH_SHORT).show()
//                var dl: designer =dimglist.get(curPos)
//                val intent = Intent(view.getContext(), detaildesigner::class.java)
//                intent.putExtra("id",dl.id)
//                intent.putExtra("age",dl.age)
//                intent.putExtra("memo",dl.memo)
//                intent.putExtra("name",dl.name)
//                intent.putExtra("phone",dl.phone)
//                intent.putExtra("year",dl.year)
//                context.startActivity(intent)

                //  Toast.makeText(parent.context,"이름 :${profile.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return dimglist.size
    }
    override fun onBindViewHolder(holder: dimgAdapter.CustomViewHolder, position: Int) {
        // holder.memo.setImageResource(1)


        holder.Dimg1.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg
        holder.Dimg2.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg
        holder.Dimg3.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg

       // Glide.with(this).load(downloadUrl).into(holder.Dimg3)
    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val Dimg1=itemView.findViewById<ImageView>(R.id.Dimg1)
        val Dimg2=itemView.findViewById<ImageView>(R.id.Dimg2)
        val Dimg3=itemView.findViewById<ImageView>(R.id.Dimg3)

    }


}