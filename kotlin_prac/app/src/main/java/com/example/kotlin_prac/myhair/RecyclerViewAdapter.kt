package com.example.myhairdiary

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_prac.R
import com.example.kotlin_prac.myhair.MyHair
import kotlinx.android.synthetic.main.activity_my_hair.*

import kotlinx.android.synthetic.main.item_schedule.*
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by WoochanLee on 22/03/2019.
 */
class RecyclerViewAdapter(val activity_my_hair: MyHair) : RecyclerView.Adapter<ViewHolderHelper>() {

    val baseCalendar = BaseCalendar()

    init {
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolderHelper(view)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.LOW_OF_CALENDAR * BaseCalendar.DAYS_OF_WEEK
    }

    override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {

        if (position % BaseCalendar.DAYS_OF_WEEK == 0) holder.tvdate.setTextColor(Color.parseColor("#ff1200"))
        else holder.tvdate.setTextColor(Color.parseColor("#676d6e"))

        if (position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate) {
            holder.tvdate.alpha = 0.3f
        } else {
            holder.tvdate.alpha = 1f
        }
        holder.tvdate.text = baseCalendar.data[position].toString()
        print(  holder.tvdate.text)
    }

    fun changeToPrevMonth() {

        baseCalendar.changeToPrevMonth {
            refreshView(it)

        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        refreshCurrentMonth(calendar)
        notifyDataSetChanged()

    }
    fun refreshCurrentMonth(calendar: Calendar) {
        val sdf = SimpleDateFormat("yyyy MM", Locale.KOREAN)

    }
}