package com.example.myhairdiary_c.main.second.findpage

import android.content.Context
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designerAdapter
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.second.second_home
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_find7page.*

class find7page : AppCompatActivity() {
// complete find 페이지 // + 어댑터를 여기다 만들었음
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find7page)
    val db= fireDB(this)
    select_designer_list(db.firestore)
    val pref=getSharedPreferences("tab2",0)
    val edit=pref.edit()
    val gender=pref.getString("gender","").toString()
    val length=pref.getString("length","").toString()
    val kind=pref.getString("kind","").toString()
    val region=pref.getString("region","").toString()
    val demand=pref.getString("demand","").toString()
    println("${gender}, ${length}, ${kind}, ${region}, ${demand}")
    retry_find.setOnClickListener(){
        var intent= Intent(this, second_home::class.java)
        startActivity(intent)
    }
    }
    public fun select_designer_list(firestore: FirebaseFirestore) {

        firestore?.collection("hair_diary").whereEqualTo("perm",1).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<designer>()
                    for(dc in it.result!!.documents){
                        dc.toObject(designer::class.java)?.let { it1 ->
                            println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }


                    findcomplete_designer.addItemDecoration(DividerItemDecoration(applicationContext,
                        HORIZONTAL))// 경계선 추가!!!!


                    findcomplete_designer.layoutManager=
                        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
                    findcomplete_designer.setHasFixedSize(true)
                    findcomplete_designer.adapter=
                        findcompleteAdapter(
                            this,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }















    class findcompleteAdapter (val context: Context, val designerList:ArrayList<designer>): RecyclerView.Adapter<findcompleteAdapter.CustomViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val view= LayoutInflater.from(parent.context).inflate(R.layout.complete_designer_adapter,parent,false)
            // 내가 쓸 custom_rev지정!
            return CustomViewHolder(
                view
            ).apply {
                itemView.setOnClickListener {
                    val curPos:Int=adapterPosition
                    var dl: designer =designerList.get(curPos)

                    val intent = Intent(view.getContext(), find8page::class.java)
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
                    edit.putString("major",dl.major)
                    edit.putInt("reviewcount",dl.reviewcount)
                    edit.putString("profile",dl.profile)
                    edit.apply()
//
//
//                    intent.putExtra("did",dl.id)
//                    intent.putExtra("age",dl.age)
//                    intent.putExtra("memo",dl.memo)
//                    intent.putExtra("name",dl.name)
//                    intent.putExtra("phone",dl.phone)
//                    intent.putExtra("index",dl.index.toString())
//                    intent.putExtra("year",dl.year)
//                    intent.putExtra("monthc",dl.monthc)
//                    intent.putExtra("major",dl.major)
//                    intent.putExtra("reviewcount",dl.reviewcount)
//                    intent.putExtra("profile",dl.profile)
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
            //   holder.memo.text=designerList.get(position).memo

            holder.dimg.setImageResource(R.drawable.ic_launcher_foreground)//designerList.get(position).dimg

            Glide.with(context).load(designerList.get(position).profile).into(holder.dimg)


        }
        class CustomViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val did=itemView.findViewById<TextView>(R.id.Did_com)
            val dimg=itemView.findViewById<ImageView>(R.id.Dimg_com)
            val name=itemView.findViewById<TextView>(R.id.Dname_com)
            val age=itemView.findViewById<TextView>(R.id.Dage_com)

        }





    }
}
