package com.example.lovelist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.bottom_nav.*

class Checked : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checked)
        botnav.getMenu().getItem(2).setChecked(true);
        botnav.setOnNavigationItemSelectedListener(this)
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
                var intent= Intent(this, unChecked::class.java)
                startActivity(intent)

            }
            R.id.bottom3->{
                var intent= Intent(this, this::class.java)
                startActivity(intent)
            }
            else->{}
        }
        return true
    }
}
