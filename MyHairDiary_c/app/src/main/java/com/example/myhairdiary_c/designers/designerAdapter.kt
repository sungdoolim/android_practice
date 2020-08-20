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


class designerAdapter (val context: Context, val designerList:ArrayList<designer>): RecyclerView.Adapter<designerAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.designer_adapter,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(
            view
        ).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var dl: designer =designerList.get(curPos)

                val intent = Intent(view.getContext(), detailedDesigner::class.java)
                val pref=context.getSharedPreferences("selected",0)
                val edit=pref.edit()
                edit.clear()

                edit.putString("did",dl.id)
                edit.putInt("age",dl.age)
                edit.putString("memo",dl.memo)
                edit.putString("name",dl.name)
                edit.putString("phone",dl.phone)
                edit.putInt("index",dl.index)
                edit.putInt("year",dl.year)
                edit.putInt("monthc",dl.monthc)
                edit.putString("memo",dl.memo)
                edit.putString("major",dl.major)
                edit.putInt("reviewcount",dl.reviewcount)
                edit.putString("profile",dl.profile)
                edit.apply()


//                intent.putExtra("did",dl.id)
//                intent.putExtra("age",dl.age)
//                intent.putExtra("memo",dl.memo)
//                intent.putExtra("name",dl.name)
//                intent.putExtra("phone",dl.phone)
//                intent.putExtra("index",dl.index.toString())
//                intent.putExtra("year",dl.year)
//                intent.putExtra("monthc",dl.monthc)
//                intent.putExtra("major",dl.major)
//                intent.putExtra("reviewcount",dl.reviewcount)
//                intent.putExtra("profile",dl.profile)
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
        holder.did.text="아이디 : "+designerList.get(position).id+"\t"
        holder.name.text="이름 : "+designerList.get(position).name+"\t"
        holder.age.text="나이 : "+designerList.get(position).age.toString()+"\t"
        holder.year.text="경력 : "+designerList.get(position).year.toString()+"\t"
        holder.phone.text="번호 : "+designerList.get(position).phone+"\t"
     //   holder.memo.text=designerList.get(position).memo
        holder.major.text="전문 분야 : "+designerList.get(position).major+"\t"
      //  holder.monthc.text="월 평균 손님 수 : "+designerList.get(position).monthc.toString()

        holder.dimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg

        Glide.with(context).load(designerList.get(position).profile).into(holder.dimg)


    }
    class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val did=itemView.findViewById<TextView>(R.id.Did)
        val dimg=itemView.findViewById<ImageView>(R.id.Dimg)
        val name=itemView.findViewById<TextView>(R.id.Dname)
        val age=itemView.findViewById<TextView>(R.id.Dage)
        val phone=itemView.findViewById<TextView>(R.id.Dphone)
        val year=itemView.findViewById<TextView>(R.id.Dyear)
        val major=itemView.findViewById<TextView>(R.id.Dmajor)

    }





}