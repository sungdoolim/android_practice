package com.example.myhairdiary.designers.ReView

import com.google.firebase.Timestamp

data class review_data(val designer_id:String="", val memo:String="", val timestamp: Timestamp = Timestamp.now(),
                       val customid:String="",val drimgurl:String="",val count:Int=0)