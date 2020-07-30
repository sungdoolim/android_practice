package com.example.kotlin_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_n_maps.*

class NMapsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_maps)

        nmap.settings.javaScriptEnabled=true
       
        nmap.webViewClient= WebViewClient()
        nmap.webChromeClient=object: WebChromeClient(){
            override fun onGeolocationPermissionsShowPrompt(
                origin: String,
                callback: GeolocationPermissions.Callback
            ) {
                // Always grant permission since the app itself requires location
                // permission and the user has therefore already granted it
                callback.invoke(origin, true, false)
            }
        }
        nmap.setClickable(false);
        nmap.loadUrl("https://m.map.naver.com/search2/search.nhn?query=미용실&siteSort=1&sm=clk#/map");
//&siteSort=1&sm=clk#/map
        mapbt.setOnClickListener(){
            nmap.loadUrl("https://m.map.naver.com/search2/search.nhn?query=미용실&siteSort="+2+"&sm=clk#/map");
        }
        mapbt2.setOnClickListener(){
            nmap.loadUrl("https://m.map.naver.com/search2/search.nhn?query=미용실&siteSort="+3+"&sm=clk#/map");
        }

    }
}
