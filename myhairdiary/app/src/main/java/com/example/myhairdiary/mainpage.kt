package com.example.myhairdiary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager

import kotlinx.android.synthetic.main.activity_my_hair.*

class mainpage : AppCompatActivity() {



        lateinit var scheduleRecyclerViewAdapter: RecyclerViewAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_my_hair)

            initView()


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
