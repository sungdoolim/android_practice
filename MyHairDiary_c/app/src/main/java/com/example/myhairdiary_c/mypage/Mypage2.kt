package com.example.myhairdiary_c.mypage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myhairdiary_c.R
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_mypage2.*
import kotlinx.android.synthetic.main.bottom_navi.*

class Mypage2 : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage2)

        val pref=getSharedPreferences("session",0)
        val profile=pref.getString("profile","").toString()
        val name=pref.getString("name","").toString()
        Glide.with(this).load(profile).into(mypage2_profile)
        mypage2_name.text=name
        botnav.getMenu().getItem(3).setChecked(true);
        goback2.setOnClickListener(){
            this.onBackPressed()
        }
        mypage2_save.setOnClickListener(){
            var iscpt=mypage2_cptbox.isChecked
            println(iscpt)
            if(iscpt){
                // 체크 여부에 따른 동의 / 비동의 처리를 해야합니다.
                // 구현되지 않았습니다.

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

            R.id.bottom1-> {
                var intent= Intent(this, Home2::class.java)
                startActivity(intent)
            }

            R.id.bottom2->
            {
                var intent= Intent(this, second_home::class.java)
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
            else ->""
        }
        return true;
    }
}
