package com.example.kotlin_prac.myhair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlin_prac.Main5Activity
import com.example.kotlin_prac.R
import com.example.myhairdiary.BaseCalendar
import com.example.myhairdiary.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_my_hair.*
import java.text.SimpleDateFormat
import java.util.*

class MyHair : AppCompatActivity() {

    lateinit var scheduleRecyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hair)

        initView()
        designers.setOnClickListener(){
            var intent= Intent(this, designers::class.java)
            startActivity(intent)
        }
        mypage.setOnClickListener(){
            var intent= Intent(this, mypage::class.java)
            startActivity(intent)

        }
    }

    fun initView() {

        scheduleRecyclerViewAdapter = RecyclerViewAdapter(this)

        rv_schedule.layoutManager = GridLayoutManager(this, BaseCalendar.DAYS_OF_WEEK)
        rv_schedule.adapter = this.scheduleRecyclerViewAdapter

        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL))
        rv_schedule.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        tv_prev_month.setOnClickListener {
            scheduleRecyclerViewAdapter.changeToPrevMonth()
        }

        tv_next_month.setOnClickListener {
            scheduleRecyclerViewAdapter.changeToNextMonth()
        }
    }

//    fun refreshCurrentMonth(calendar: Calendar) {
//        val sdf = SimpleDateFormat("yyyy MM", Locale.KOREAN)
//        tv_current_month.text = sdf.format(calendar.time)
//    }
}