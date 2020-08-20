package com.example.lovelist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_un_checked.*
import kotlinx.android.synthetic.main.bottom_nav.*

class unChecked : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_un_checked)
        botnav.getMenu().getItem(1).setChecked(true);
        botnav.setOnNavigationItemSelectedListener(this)
        selectList(this)
    }
    override fun onBackPressed() {
        var intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bottom1->{
                var intent= Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.bottom2->{
                var intent= Intent(this, this::class.java)
                startActivity(intent)

            }
            R.id.bottom3->{
                var intent= Intent(this, Checked::class.java)
                startActivity(intent)
            }
            else->{}
        }
        return true
    }
    fun selectList(container: Context){
        val firestore= FirebaseFirestore.getInstance()
        firestore?.collection("ourlist").orderBy("index").get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    var userDTO=ArrayList<list_data>()
                    for(dc in it.result!!.documents){
                        dc.toObject(list_data::class.java)?.let { it1 ->
                            if(!it1.ischecked){
                                userDTO.add(it1)
                            }
                        }
                    }

                    unchecked_rv.layoutManager=
                        LinearLayoutManager(container, LinearLayoutManager.VERTICAL,false)
                    unchecked_rv.setHasFixedSize(true)
                    unchecked_rv.adapter=
                        list_adapter(
                            container,
                            userDTO
                        )
                }else{
                    println("fail")
                }
            }
    }
}