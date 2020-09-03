package com.example.myhairdiary_c.main.second

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.designers.designer_list
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.findpage.find1page
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_second_home.*
import kotlinx.android.synthetic.main.bottom_navi.*

class second_home : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_home)
        botnav.getMenu().getItem(1).setChecked(true);
        botnav.setOnNavigationItemSelectedListener(this)
        val pref=getSharedPreferences("session",0)
        val id=pref.getString("id","")
        Main_greet.text=id+" "+Main_greet.text

        findmydesigner.setOnClickListener(){
            var intent= Intent(this, find1page::class.java)
            startActivity(intent)
        }
        findalldesigner.setOnClickListener(){
            var intent= Intent(this, designer_list::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
        finish()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1-> {
                var intent= Intent(this, Home2::class.java)
                startActivity(intent)
            }

            R.id.bottom2->
            {
                var intent= Intent(this, this::class.java)
                startActivity(intent)
            }
            R.id.bottom3->{
                var intent= Intent(this, MyHairDiary::class.java)
                startActivity(intent)
            }
            R.id.bottom4->{
                var intent= Intent(this, Mypage::class.java)
                startActivity(intent)


            }
//            R.id.bottom5->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            else ->""
        }
        return true;
    }
}
