package com.apps.smartclassroom.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.apps.smartclassroom.R
import com.apps.smartclassroom.util.WEBVIEW_PREFIX
import kotlinx.android.synthetic.main.activity_viewnotes.*

class Viewnotes : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewnotes)

        val url = intent.getStringExtra("NOTES_URL")
        wv_notes.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                wv_notes.visibility = View.VISIBLE
                cl_load.visibility = View.GONE

            }



            override fun onReceivedError(
                view: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(view, errorCode, description, failingUrl)

                Toast.makeText(this@Viewnotes, description, Toast.LENGTH_SHORT).show()
            }
        }

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