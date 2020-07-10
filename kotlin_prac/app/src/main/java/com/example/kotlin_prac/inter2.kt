package com.example.kotlin_prac

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface inter2 {// /web/androidtest.json
    @GET("/web/androidtest.do")
    fun requestAllData(@Query("id") id:String="s",@Query("pw")pw:String="_pwd") : Call<PhotoModel>

    /*
    @POST(~/{id})
    @Query("id") id: String ="s"
     */
}

