package com.example.kotlin_prac

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Spring : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spring)
        println("--------------------------------------------------------------------넘어옴 ")
        f()


    }

    fun f(){
        var retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.56.1:8052")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(inter::class.java)
        retrofitService.requestAllData().enqueue(object : Callback<PhotoModel> {
            override fun onResponse(call: Call<PhotoModel>, response: Response<PhotoModel>) {
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse")
                if (response.isSuccessful) {
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse success")

                    val body = response.body()
                    val jsonObj = JSONTokener(body.toString())

                    body?.let {
                        //text_text.text = body.toString response 잘 받아왔는지 확인.
                        println(body.toString())
                        println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse    let")

                    }
                }
                else {
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse   else")
                }
                }
            override fun onFailure(call: Call<PhotoModel>, t: Throwable) {
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~fail")
                Log.d("this is error",t.message)
            }


        })
    }}

