package com.example.template_prac

import android.content.ClipData
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerbt.setOnClickListener(){
            layout_drawer.openDrawer(GravityCompat.START)// END는 오른쪽
        }




        navi.setNavigationItemSelectedListener (this)

        botnav.setOnNavigationItemSelectedListener(this)


    }




     override fun onNavigationItemSelected(p0: MenuItem): Boolean {
 // 메서드가 통일 된것!!!!!

         p0.setChecked(true) // 이게 선택 된거 표시해주는거...ㅜㅠㅠㅠㅠㅠㅠㅠㅠㅠ
         when(p0.itemId){
             R.id.drawer_prac_xml-> Toast.makeText(this,"접근",Toast.LENGTH_SHORT).show()
             R.id.drawer_prac_xml2-> Toast.makeText(this,"메일",Toast.LENGTH_SHORT).show()
             R.id.drawer_prac_xml3-> Toast.makeText(this,"문자",Toast.LENGTH_SHORT).show()




             R.id.num1->supportFragmentManager.beginTransaction().replace(R.id.framelayout,farg1()).commit()// fragment로 화면 전환 bottomnavi
             R.id.num2->supportFragmentManager.beginTransaction().replace(R.id.framelayout,frag2()).commit()

         }
         layout_drawer.closeDrawers()
         return false;
     }


}








