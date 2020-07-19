package com.example.myhairdiary.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.example.myhairdiary.register.Register
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {
val calendar: Calendar= Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        var y=""
        var m=""
        var d=""

        calendarView.setOnDateChangeListener{view,year,month,dayOfMonth->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month+1)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            Log.d("1", calendar.get(Calendar.YEAR).toString())
            Log.d("2", calendar.get(Calendar.MONTH).toString())
            Log.d("3", calendar.get(Calendar.DAY_OF_MONTH).toString())
           y=calendar.get(Calendar.YEAR).toString()
            m=calendar.get(Calendar.MONTH).toString()
          d=calendar.get(Calendar.DAY_OF_MONTH).toString()

            print(calendar.get(Calendar.YEAR).toString())
            print(calendar.get(Calendar.MONTH).toString())
            print(calendar.get(Calendar.DAY_OF_MONTH).toString())

        }

        submitlist.setOnClickListener(){
            var memo=todoEditText.text
            var firestore = FirebaseFirestore.getInstance()
            var time=hour.text.toString()+" : "+minute.text.toString()+" ~ "+hour2.text.toString()+" : "+minute2.text.toString()


            if(Integer.parseInt(m)<10){m="0"+m}
            if(Integer.parseInt(d)<10){d="0"+d}
            var date=y+m+d
            var dateint=Integer.parseInt(date)

            var customer=customname.text
            val pref=getSharedPreferences("ins",0)
            var sesid=pref.getString("id","null")
            createData(sesid!!,dateint,memo.toString(),time,customer.toString(),firestore)
        }
        todolist.setOnClickListener(){

            if(Integer.parseInt(m)<10){m="0"+m}
            if(Integer.parseInt(d)<10){d="0"+d}
            var date=y+m+d
            var dateint=Integer.parseInt(date)

            var intent=Intent(this,CalbasicActivity::class.java)
            intent.putExtra("dateint",dateint)

            startActivity(intent)

        }


    }
    public fun createData( id:String, date:Int,memo:String,time:String,customer:String,firestore:FirebaseFirestore){// 실제 되는거 확인 했음
        var userDTO=
            calist(id, date, memo, time, customer)
        // 밑에 document를 공백으로 두면 임의의 아이디를 생성해서 추가함
        firestore?.collection("hair_cal")?.document()?.set(userDTO)
            .addOnCompleteListener {
                if(it.isSuccessful)
                    print("create성공")
            }
    }
}
