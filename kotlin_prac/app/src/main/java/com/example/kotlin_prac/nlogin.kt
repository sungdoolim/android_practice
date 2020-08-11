package com.example.kotlin_prac

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_nlogin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class nlogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nlogin)

        wvnaver.settings.javaScriptEnabled=true
        wvnaver.webViewClient= WebViewClient()
        wvnaver.webChromeClient= WebChromeClient()
      //  wvnaver.loadUrl("https://www.naver.com")
       wvnaver.loadUrl("http://172.30.1.8:8052/web/nlogin.do")
        val webSettings: WebSettings = wvnaver.getSettings()
        webSettings.javaScriptEnabled = true
        wvnaver.addJavascriptInterface(AndroidBridge(), "MyTestApp")
        // 웹뷰에서 이클립스에 MyTestApp으로 보내는 메서드가 있어!
        // naverSuccess.jsp 안에 스크립트로 존재하고 CallAndroid() 로 정보 보내기!


        val mHandler: Handler = object : Handler() {
            override fun handleMessage(msg: Message?) {  // 실행이 끝난후 확인 가능
            }
        }
        Handler().postDelayed(Runnable
        // 1 초 후에 실행
        {   f()
          //  var a=wvnaver.
            // 실행할 동작 코딩
            mHandler.sendEmptyMessage(0) // 실행이 끝난후 알림
        }, 10000
        )



    }

    class AndroidBridge {

        @JavascriptInterface
       fun AlertMsg(arg:String) { // 웹뷰내의 페이지에서 호출하는 함수
            Handler().post( Runnable() {
                println("log : :--------------------------------------------------------------------------------  : ${arg}")
//                    Toast.makeText( this,, Toast.LENGTH_SHORT).show();
            });
        }
    }




    fun f(){// 단일 클래스를 받음

        var retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.8:8052")//http://192.168.56.1:8052
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitService = retrofit.create(inter_naverlogin::class.java)// 내가만든 inter 클래스 사용
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
    }



}
