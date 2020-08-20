package com.example.template_prac

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlin_prac.R
import kotlinx.android.synthetic.main.frag1.*

public class frag1 : Fragment() {
    override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ):
            View? {
        var view= inflater.inflate(R.layout.frag1,container,false);
        val tv=view.findViewById<TextView>(R.id.textView1)
        val bt= view?.findViewById<Button>(R.id.fragbt1)
        val transaction= activity?.supportFragmentManager?.beginTransaction()


        if(arguments!=null){
            tv.text= arguments!!.getString("fromFrag2")
        }

            bt!!.setOnClickListener(){
                var bundle=Bundle()
                bundle.putString("fromFrag1","뭐죠 이건??? from frag1")
                var f2=frag2()
                f2.arguments=bundle
                transaction?.replace(R.id.framelayout,f2)
                transaction?.commit()


        }



        return view
        //return inflater.inflate(R.layout.frag1,container,false);


    }
}