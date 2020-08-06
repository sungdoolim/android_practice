package com.example.myhairdiary_c.frag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lovelist.R
import com.example.lovelist.insertpage
import com.example.lovelist.list_adapter
import com.example.lovelist.list_data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.home.*

class home: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {





        var param= arguments?.getString("param")
        println("param : ${param}")
        val fragment: Fragment = home() // Fragment 생성
        val bundle=Bundle()
        bundle.putString("param2", "dfsef") // Key, Value
        fragment.arguments = bundle


        return inflater.inflate(R.layout.home,container,false)

    }
    fun selectList(container: ViewGroup?){
        val firestore= FirebaseFirestore.getInstance()
        firestore?.collection("ourlist").orderBy("index").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<list_data>()
                    for(dc in it.result!!.documents){
                        dc.toObject(list_data::class.java)?.let { it1 ->
                            // println("reviewcount : ${it1.reviewcount}")
                            userDTO.add(it1) } // println("success ${userDTO[len].toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                    }
                    println("designers  len = ${userDTO.size}")

                    // recommend_designer_list 는 id로 얻어온 recyclerview 임

                    listrv.layoutManager=
                        LinearLayoutManager(container?.context, LinearLayoutManager.HORIZONTAL,false)
                    listrv.setHasFixedSize(true)
                    listrv.adapter=
                        list_adapter(
                            container!!.context,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}