package com.example.myhairdiary.designers.trace

import com.google.firebase.Timestamp
import com.google.firebase.Timestamp.now

import java.util.*

data class feedData(val id:String="", val customid:String="", val timestamp: Timestamp= Timestamp.now(), val memo:String="",val count:Int=0
)