package com.example.myhairdiary_c.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.main.second.second_home
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_mypage2.*
import kotlinx.android.synthetic.main.bottom_navi.*

class Mypage2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage2)
        mypage2_save.setOnClickListener(){
            var iscpt=mypage2_cptbox.isChecked
            println(iscpt)
            if(iscpt){
            Toast.makeText(this,"체크됨",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this,"체크 해제됨",Toast.LENGTH_SHORT).show()
            }
        }
        botnav.setOnNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.bottom2->
            {
                var intent= Intent(this, second_home::class.java)
                startActivity(intent)
            }
//            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
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
