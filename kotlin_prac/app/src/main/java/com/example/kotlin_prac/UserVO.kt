package com.example.kotlin_prac

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserVO(var title:String){
    @PrimaryKey(autoGenerate=true)
    var id:Int=0

    
}
