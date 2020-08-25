package com.example.myhairdiary_c

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myhairdiary_c.frag.home

import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_navi.*
import kotlinx.android.synthetic.main.home.*
/*home frag trying....*/
// 안씁니다
class Home : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
     private var arraylist: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        botnav.setOnNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
        var pref=getSharedPreferences("session",0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.bottom1->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()// fragment로 화면 전환 bottomnavi
            R.id.bottom2->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom3->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
            R.id.bottom4->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()

            else ->supportFragmentManager.beginTransaction().replace(R.id.framelayout, home()).commit()
        }
        return true;
    }
    fun search(charText: String,list:ArrayList<String>) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list!!.clear()

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length == 0) {
            list!!.addAll(arraylist!!)
        } else {
            // 리스트의 모든 데이터를 검색한다.
            for (i in arraylist!!.indices) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist!![i].toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list!!.add(arraylist!![i])
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.

    }

    private fun settingList(list:ArrayList<String>) :ArrayList<String>{
        list?.add("채수빈");
        list?.add("박지현");
        list?.add("수지");
        list?.add("남태현");
        list?.add("하성운");
        list?.add("크리스탈");
        list?.add("강승윤");
        list?.add("손나은");
        list?.add("남주혁");
        list?.add("루이");
        list?.add("진영");
        list?.add("슬기");
        list?.add("이해인");
        list?.add("고원희");
        list?.add("설리");
        list?.add("공명");
    return list
    }

}
