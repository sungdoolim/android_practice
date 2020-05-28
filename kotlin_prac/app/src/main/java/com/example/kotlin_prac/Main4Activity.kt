package com.example.kotlin_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main4.*

class Main4Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        var str=loadData()
        tv.setText(str)
        wv.settings.javaScriptEnabled=true
        wv.webViewClient= WebViewClient()
        wv.webChromeClient= WebChromeClient()
        wv.loadUrl("https://www.naver.com")


    }

    override fun onBackPressed() {
        if(wv.canGoBack()){
            wv.goBack()
        }else{
            super.onBackPressed()
        }


    }

    fun saveData(){
    val pref=getSharedPreferences("pref",0)
    var edit=pref.edit()
        edit.putString("name","inner db test = prefS")
        edit.apply()

    }
    fun loadData(): String? {
        val pref=getSharedPreferences("pref",0)
        var str=pref.getString("name","defValue!!!!")
        return str

    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
    }
}
