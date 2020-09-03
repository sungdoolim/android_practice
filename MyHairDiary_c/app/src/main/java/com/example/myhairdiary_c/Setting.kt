package com.example.myhairdiary_c

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.myhairdiary_c.diary.MyHairDiary
import com.example.myhairdiary_c.main.Home2
import com.example.myhairdiary_c.main.second.second_home
import com.example.myhairdiary_c.mypage.Mypage
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.bottom_navi.*

class Setting : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    // 설정 페이지 입니다.
    // 각종 버튼이벤트가 추가되어야 합니다.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        // 여러 잡다한 사항들? 이 제시됩니다.
        // 아마 텍스트 하나하나 화면 전환이 일어날 예정이지 않을까 생각합니다.

        botnav.setOnNavigationItemSelectedListener(this)
        botnav.getMenu().getItem(3).setChecked(true);
        imageView8.setOnClickListener(){
            onBackPressed()
        }
        imageView9.setOnClickListener(){
            var intent=Intent(this,Home2::class.java)
            startActivity(intent)
        }
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
