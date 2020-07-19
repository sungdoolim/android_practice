package com.example.myhairdiary.calendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhairdiary.R

import kotlinx.android.extensions.LayoutContainer


/**
 * Created by WoochanLee on 22/03/2019.
 */
open class ViewHolderHelper(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
val tvdate=itemView.findViewById<TextView>(R.id.tvdate);


}