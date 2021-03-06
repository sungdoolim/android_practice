package com.example.kotlin_prac

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.kotlin_prac.myhair.MyHair
import com.example.template_prac.frag1
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import io.reactivex.disposables.CompositeDisposable

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.appbar_prac.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    val disposables: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction=supportFragmentManager.beginTransaction()
        val f1= frag1()
        transaction.replace(R.id.framelayout,f1)
        transaction.commit()

        appbarbt1.setOnClickListener(){
            var intent=Intent(this, MyHair::class.java)
            startActivity(intent)
        }
        appbarbt2.setOnClickListener(){
            // 구글맵
            var intent=Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
        appbarbt3.setOnClickListener(){
            // 네이버 웹뷰
            var intent=Intent(this, NMapsActivity::class.java)
            startActivity(intent)
        }

        fire.setOnClickListener(){
            var intent=Intent(this,fireActivity::class.java)
            startActivity(intent)
        }

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

//                var str2=db.userDao().getAll();
//            var str3=str2.value;
//            var b= str3!![3].title;

         //   println(str)
          //  println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!?")
        }
        db.userDao().getAll().observe(this, Observer {
            //str=db.userDao().getAll().toString();
            str=it.toString()
           // it[1].id

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
        botnav.setOnNavigationItemSelectedListener(this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // 메서드가 통일 된것!!!!! drawer + bottomnavi
        when(item.itemId){
            R.id.access->{ Toast.makeText(this,"접근",Toast.LENGTH_SHORT).show()
                var intent=Intent(this,graph_prac::class.java)
                startActivity(intent)


            }
            R.id.email-> {
                Toast.makeText(this,"메일",Toast.LENGTH_SHORT).show()
                var intent=Intent(this,nlogin::class.java)
                startActivity(intent)


            }
            R.id.message-> Toast.makeText(this,"문자",Toast.LENGTH_SHORT).show()

         //   R.id.num1->supportFragmentManager.beginTransaction().replace(R.id.framelayout, farg1()).commit()// fragment로 화면 전환 bottomnavi
          //  R.id.num2->supportFragmentManager.beginTransaction().replace(R.id.framelayout, frag2()).commit()

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

//    private fun getHashKey() {
//        var packageInfo: PackageInfo? = null
//        try {
//            packageInfo =
//                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
//        for (signature in packageInfo!!.signatures) {
//            try {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//            } catch (e: NoSuchAlgorithmException) {
//                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
//            }
//        }
//    }


}
