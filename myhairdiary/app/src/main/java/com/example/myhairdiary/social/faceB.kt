package com.example.myhairdiary.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import kotlinx.android.synthetic.main.activity_face_b.*

class faceB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_b)
        wvface.settings.javaScriptEnabled=true
        wvface.webViewClient= WebViewClient()
        wvface.webChromeClient= WebChromeClient()
        wvface.loadUrl("https://www.facebook.com/profile.php?id=100011043121912")

    }
}
