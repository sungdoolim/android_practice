package com.example.myhairdiary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myhairdiary.R
import java.util.*

class ProfilesAdapter (val profileList: ArrayList<profiles>): RecyclerView.Adapter<ProfilesAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder( parent: ViewGroup,   viewType: Int): ProfilesAdapter.CustomViewHolder {
     val view=LayoutInflater.from(parent.context).inflate(R.layout.custom_rev,parent,false)
        // 내가 쓸 custom_rev지정!
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos:Int=adapterPosition
                var profile:profiles=profileList.get(curPos)
                Toast.makeText(parent.context,"이름 :${profile.name}",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return profileList.size
    }
    override fun onBindViewHolder(holder: ProfilesAdapter.CustomViewHolder, position: Int) {
        holder.gender.setImageResource(profileList.get(position).gender)
        holder.name.text=profileList.get(position).name
        holder.age.text=profileList.get(position).age.toString()
        holder.job.text=profileList.get(position).job
    }
    class CustomViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val gender=itemView.findViewById<ImageView>(R.id.iv_profile)
        val name=itemView.findViewById<TextView>(R.id.tv_name)
        val age=itemView.findViewById<TextView>(R.id.tv_age)
        val job=itemView.findViewById<TextView>(R.id.tv_job)
    }
}