package com.example.kotlin_prac

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
public interface UserDAO{
 @Query("select * from UserVO")
 fun getAll(): LiveData<List<UserVO>>;
    @Insert
    fun insert(uservo:UserVO)
    @Update
    fun update(uservo:UserVO)
    @Delete
    fun delete(uservo:UserVO)


}