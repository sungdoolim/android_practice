package com.example.kotlin_prac

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=arrayOf(UserVO::class),version=1)
abstract class Roomdb:RoomDatabase(){
    abstract fun userDao():UserDAO

}