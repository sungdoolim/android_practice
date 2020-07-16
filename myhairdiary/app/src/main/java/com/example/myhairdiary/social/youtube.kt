package com.example.myhairdiary.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import kotlinx.android.synthetic.main.activity_face_b.*
import kotlinx.android.synthetic.main.activity_youtube.*

class youtube : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)

        wvyoutube.settings.javaScriptEnabled=true
        wvyoutube.webViewClient= WebViewClient()
        wvyoutube.webChromeClient= WebChromeClient()
        wvyoutube.loadUrl("https://www.youtube.com/channel/UC_7dp9NRfrnuWEZNEPZbYlA")

    }
    //https://www.youtube.com/channel/UC_7dp9NRfrnuWEZNEPZbYlA
}
