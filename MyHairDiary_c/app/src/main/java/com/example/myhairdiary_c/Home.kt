package com.example.myhairdiary_c

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.myhairdiary_c.frag.home
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var pref=getSharedPreferences("session",0)
        botnav.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()// fragment로 화면 전환 bottomnavi
            R.id.bottom2->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom4->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom5->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            else ->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
        }
        return true;
    }

}
