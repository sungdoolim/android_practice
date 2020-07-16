package com.example.myhairdiary.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import kotlinx.android.synthetic.main.activity_face_b.*
import kotlinx.android.synthetic.main.activity_insta.*

class insta : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insta)
        wvinsta.settings.javaScriptEnabled=true
        wvinsta.webViewClient= WebViewClient()
        wvinsta.webChromeClient= WebChromeClient()
        wvinsta.loadUrl("https://www.instagram.com/gimseongmin4250/?hl=ko")

    }
}
