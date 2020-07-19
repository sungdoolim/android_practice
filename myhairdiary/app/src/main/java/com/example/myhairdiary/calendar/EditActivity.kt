package com.example.myhairdiary.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myhairdiary.R
import kotlinx.android.synthetic.main.activity_edit.*
import java.util.*

class EditActivity : AppCompatActivity() {
val calendar: Calendar= Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        calendarView.setOnDateChangeListener{view,year,month,dayOfMonth->
            calendar.set(Calendar.YEAR,year)
            calendar.set(Calendar.MONTH,month+1)
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            Log.d("1", calendar.get(Calendar.YEAR).toString())
            Log.d("2", calendar.get(Calendar.MONTH).toString())
            Log.d("3", calendar.get(Calendar.DAY_OF_MONTH).toString())
            print(calendar.get(Calendar.YEAR).toString())
            print(calendar.get(Calendar.MONTH).toString())
            print(calendar.get(Calendar.DAY_OF_MONTH).toString())
        }
    }
}
