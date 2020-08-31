package com.example.lovelist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_un_checked.*
import kotlinx.android.synthetic.main.activity_un_checked.layout_drawer
import kotlinx.android.synthetic.main.activity_un_checked.naviView
import kotlinx.android.synthetic.main.bottom_nav.*

class unChecked : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    var Ltoast: Toast? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_un_checked)

        val pref=getSharedPreferences("Rnd",0)
        val edit=pref.edit()
        edit.putString("id","누꿍")
        edit.apply()
        Toast.makeText(this,"누꿍 안냥?",Toast.LENGTH_SHORT).show()


        botnav.getMenu().getItem(1).setChecked(true);
        botnav.setOnNavigationItemSelectedListener(this@unChecked)

        naviView.setNavigationItemSelectedListener (this@unChecked)
        selectList(this@unChecked)
        insertone?.setOnClickListener(){
            var intent= Intent(this, insertpage::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this@unChecked)
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav1->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this@unChecked,"녕이야~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav2->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this@unChecked,"내가내가~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav3->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this@unChecked,"마니나미 사랑해~~~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav4->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this@unChecked,"군데군데~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }
            R.id.nav5->{
                Ltoast?.cancel()
                Ltoast= Toast.makeText(this@unChecked,"녕이눈~~~~", Toast.LENGTH_SHORT)
                Ltoast?.show()
            }

            R.id.bottom1->{
                var intent= Intent(this@unChecked, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.bottom2->{
                var intent= Intent(this@unChecked, this::class.java)
                startActivity(intent)

            }
            R.id.bottom3->{
                var intent= Intent(this@unChecked, Checked::class.java)
                startActivity(intent)
            }
            R.id.hama->{
                val pref=getSharedPreferences("Rnd",0)
                val edit=pref.edit()
                edit.putString("id","누꿍")
                edit.apply()
                Toast.makeText(this@unChecked,"누꿍 안냥?", Toast.LENGTH_SHORT).show()

                layout_drawer.closeDrawers()
            }
            R.id.staris->{
                val pref=getSharedPreferences("Rnd",0)
                val edit=pref.edit()
                edit.putString("id","내꿍")
                edit.apply()
                Toast.makeText(this@unChecked,"냐오옹 이거아냐아아아 저리가아ㅏㅇ아", Toast.LENGTH_SHORT).show()

                layout_drawer.closeDrawers()
            }else->{
            Ltoast?.cancel()

            Ltoast= Toast.makeText(this@unChecked,"바보멍청이말미쟐해삼!!!!꺆!", Toast.LENGTH_SHORT)
            Ltoast?.show()

            layout_drawer.closeDrawers()
        }
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