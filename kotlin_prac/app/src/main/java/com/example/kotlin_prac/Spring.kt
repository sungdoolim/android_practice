package com.example.kotlin_prac

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
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
        f2()


    }

    fun f2(){// 리스트를 받음
        var retrofit = Retrofit.Builder()
            .baseUrl("http://staris.freehongs.net")//http://192.168.56.1:8052
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(inter2::class.java)//내가만든 inter2 클래스 사용
        retrofitService.requestAllData().enqueue(object : Callback<PhotoModel> {
            @RequiresApi(Build.VERSION_CODES.KITKAT)
            override fun onResponse(call: Call<PhotoModel>, response: Response<PhotoModel>) {
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse this is f2")
                if (response.isSuccessful) {
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse success")

                    val body = response.body()

                    //val jsonObj = JSONTokener(body.toString())
                    val jsonObj =  JSONObject.wrap(body?.sendData)
                    println("jsonobj:${jsonObj}")
                    val jArray = jsonObj as JSONArray
                    println(jArray.length())
                    var ptmp :PhotoModel
                    var tmpar= arrayListOf<PhotoModel>()
                    for(i in 0..jArray.length()-1){
                        ptmp= PhotoModel(jArray.getJSONObject(i).getString("f"),jArray.getJSONObject(i).getString("l"),null,null)
                        tmpar.add(ptmp)
                    }
                    for(i in 0..tmpar.size-1){
                        println(tmpar.get(i).toString())
                    }

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
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~fail f2")
                Log.d("this is error",t.message)
            }


        })
    }
    fun f(){// 단일 클래스를 받음

        var retrofit = Retrofit.Builder()
            .baseUrl("http://staris.freehongs.net")//http://192.168.56.1:8052
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(inter::class.java)// 내가만든 inter 클래스 사용
        retrofitService.requestAllData().enqueue(object : Callback<PhotoModel> {
            override fun onResponse(call: Call<PhotoModel>, response: Response<PhotoModel>) {
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse this is f")
                if (response.isSuccessful) {
                    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~onresponse success")

                    val body = response.body()
                    //val jsonObj = JSONTokener(body.toString())x`
                  //  val jsonObj =  JSONObject.quote(body.toString());
                 //   println("jsonobj:${jsonObj}")
                 //   val jArray = jsonObj[2]   as JSONArray?
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
                println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~fail f")
                Log.d("this is error",t.message)
            }


        })
    }}

