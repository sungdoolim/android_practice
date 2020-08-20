package com.example.template_prac

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlin_prac.R
import kotlinx.android.synthetic.main.frag2.*

class frag2 :Fragment(){
    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ):
            View? {
        var view= inflater.inflate(R.layout.frag2,container,false);
        val tv=view.findViewById<TextView>(R.id.textView2)
        val bt=view?.findViewById<Button>(R.id.fragbt2)
        if(arguments!=null){
            val result= arguments!!.getString("fromFrag1")
            tv.text=result.toString()
        }
        bt!!.setOnClickListener(){
            var bundle=Bundle()
            bundle.putString("fromFrag2","뭐죠 이건??? from frag2")
            val transaction= activity?.supportFragmentManager?.beginTransaction()
            var f1=frag1()
            f1.arguments=bundle
            transaction?.replace(R.id.framelayout,f1)
            transaction?.commit()
        }
        return view
    }

}