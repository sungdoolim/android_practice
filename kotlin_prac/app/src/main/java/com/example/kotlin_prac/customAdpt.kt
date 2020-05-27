package com.example.kotlin_prac

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class customAdpt(val context: Context,val Userl:ArrayList<Users> ):BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
      //  TODO("Not yet implemented")
        val view =LayoutInflater.from(context).inflate(R.layout.custom_lv ,null)

        var profile=Userl[position].profile
        var name=Userl[position].name
        var age=Userl[position].age
       // var cname=
        var vimg=view.findViewById<ImageView>(R.id.img)
        var vname=view.findViewById<TextView>(R.id.cusname)
        var vage=view.findViewById<TextView>(R.id.cusage)
        vimg.setImageResource(profile)
        vname.text=name
        vage.text=age.toString()


        return view
    }

    override fun getItem(position: Int): Any {
        //TODO("Not yet implemented")
        return Userl[position]
    }

    override fun getItemId(position: Int): Long {
        //TODO("Not yet implemented")
    return 0
    }

    override fun getCount(): Int {
       // TODO("Not yet implemented")
    return Userl.size
    }


}