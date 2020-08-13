package com.example.lovelist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhairdiary_c.frag.home
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_nav.*


class MainActivity : AppCompatActivity()  ,NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
        selectList(this)
        insertone?.setOnClickListener(){
            var intent= Intent(this, insertpage::class.java)
            startActivity(intent)
        }

        val pref=getSharedPreferences("Rnd",0)
        val edit=pref.edit()
        edit.putString("id","누꿍")
        edit.apply()
        Toast.makeText(this,"누꿍 안냥?",Toast.LENGTH_LONG).show()
        botnav.setOnNavigationItemSelectedListener(this)
        naviView.setNavigationItemSelectedListener (this)
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        finish()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.bottom1->{
                var intent= Intent(this, this::class.java)
                startActivity(intent)
                   }
            R.id.bottom2->{
                var intent= Intent(this, unChecked::class.java)
                startActivity(intent)

                   }
            R.id.bottom3->{
                var intent= Intent(this, Checked::class.java)
                startActivity(intent)
            }
            R.id.hama->{
                val pref=getSharedPreferences("Rnd",0)
                val edit=pref.edit()
                edit.putString("id","누꿍")
                edit.apply()
                Toast.makeText(this,"누꿍 안냥?",Toast.LENGTH_LONG).show()
            }
            R.id.staris->{
                val pref=getSharedPreferences("Rnd",0)
                val edit=pref.edit()
                edit.putString("id","내꿍")
                edit.apply()
                Toast.makeText(this,"냐오옹 이거아냐아아아 저리가아ㅏㅇ아",Toast.LENGTH_LONG).show()
            }else->{}
    }
        layout_drawer.closeDrawers()
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
                            userDTO.add(it1) }
                    }

                    listrv.layoutManager=
                        LinearLayoutManager(container, LinearLayoutManager.VERTICAL,false)
                    listrv.setHasFixedSize(true)
                    listrv.adapter=
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
