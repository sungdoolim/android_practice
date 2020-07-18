package com.example.myhairdiary.designers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detaildesigner.*
import kotlinx.android.synthetic.main.activity_dimg_adpt_list.*

class dimg_adpt_list : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dimg_adpt_list)

        var firestore = FirebaseFirestore.getInstance()
        selectList(firestore)

    }
    public fun selectList(firestore:FirebaseFirestore) {
        println("read")

        val profileList=arrayListOf(
            Dimgs(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground),
            Dimgs(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground),
            Dimgs(R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground)

            )


       // dimglistrv.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        designerimg_rv.setHasFixedSize(true)
        val mLayoutManager =  LinearLayoutManager(this);
        designerimg_rv.layoutManager = mLayoutManager;
        designerimg_rv.adapter=dimgAdapter(profileList)

        println("read end")

    }
}
