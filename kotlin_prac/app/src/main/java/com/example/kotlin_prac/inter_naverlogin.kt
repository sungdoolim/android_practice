package com.example.kotlin_prac

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface inter_naverlogin {// /web/androidtest.json
    @GET("/web/andtest.do")
    fun requestAllData(@Query("id") id:String="안드로이드에서 보낸 id",@Query("pw")pw:String="_pwd") : Call<PhotoModel>
//여기서는 보내고 받아오는 거구나!!   id와 pw를 보내는거야!!!
    /*
    @POST(~/{id})
    @Query("id") id: String ="s"
     */
}

