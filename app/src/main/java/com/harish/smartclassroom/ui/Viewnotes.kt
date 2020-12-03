package com.harish.smartclassroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import com.harish.smartclassroom.R
import com.harish.smartclassroom.util.WEBVIEW_PREFIX
import kotlinx.android.synthetic.main.activity_viewnotes.*

class Viewnotes : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewnotes)

        val url = intent.getStringExtra("NOTES_URL")
        wv_notes.webViewClient = WebViewClient()

       if(!url.isNullOrEmpty())
        wv_notes.loadUrl(WEBVIEW_PREFIX+url)
       else
           Toast.makeText(this, "Empty file", Toast.LENGTH_SHORT).show()


        wv_notes.settings.javaScriptEnabled = true

        wv_notes.settings.setSupportZoom(true)




    }

    override fun onBackPressed() {
        if (wv_notes.canGoBack())
        wv_notes.goBack()
        else
        super.onBackPressed()
    }
}