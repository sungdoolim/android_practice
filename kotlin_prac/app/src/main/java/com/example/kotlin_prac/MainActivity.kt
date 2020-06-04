package com.example.kotlin_prac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Dispatcher


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val db= Room.databaseBuilder(
            applicationContext,
            Roomdb::class.java,"firstdb"
        ).build()
        var str:String?;


        roomdb.setOnClickListener(){
        //    println("???????????????????????????????????????????????????????????????")
       var j=lifecycleScope.launch(Dispatchers.IO) {
           print("1")
           db.userDao().insert(UserVO(insertroot.text.toString()))
           print("2")
       }
          //  print("j가 뭘까...${j.toString()}")

            //    str=db.userDao().getAll().toString();
         //   println(str)
          //  println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?")
        }
        db.userDao().getAll().observe(this, Observer {
            //str=db.userDao().getAll().toString();
            str=it.toString()
            println(str)
        })
        spring.setOnClickListener(){
            var intent=Intent(this,Spring::class.java)

            startActivity(intent)
        }
        rec.setOnClickListener(){
            var intent=Intent(this,Main5Activity::class.java)

            startActivity(intent)
        }

        pref.setOnClickListener(){
            val intent=Intent(this,Main4Activity::class.java)
            val pref=getSharedPreferences("pref",0)
            var edit=pref.edit()
            edit.putString("name","inner db test = prefS from main 1")
            edit.apply()



            startActivity(intent)
        }

        tv.setText("hi")
        et.setText("val")
        bt1.setOnClickListener(){

            var str=et.getText()
            tv.setText(str)
            println("--------------------------------------------------------------------------버튼 활용-------${et.getText()}")
        }
        bt2.setOnClickListener(){
            var intent=Intent(this,Main2Activity::class.java)
            intent.putExtra("intentvalue","hi second?")
            startActivity(intent)

        }
        bt3.setOnClickListener(){
            var intent=Intent(this,Main3Activity::class.java)
            startActivity(intent)
        }
        btn_navi.setOnClickListener{
            layout_drawer.openDrawer(GravityCompat.START)// END는 오른쪽

        }
        naviView.setNavigationItemSelectedListener (this) // layout쪽 맨 밑에 가보면 menu/navi_menu로 연결됨을 확인 가능



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
       // TODO("Not yet implemented")
        when(item.itemId){
            R.id.access-> Toast.makeText(this,"접근",Toast.LENGTH_SHORT).show()
            R.id.email-> Toast.makeText(this,"메일",Toast.LENGTH_SHORT).show()
            R.id.message-> Toast.makeText(this,"문자",Toast.LENGTH_SHORT).show()

        }
        layout_drawer.closeDrawers()
        return false;

    }

    override fun onBackPressed() {
        if(layout_drawer.isDrawerOpen(GravityCompat.START)){
            layout_drawer.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}
