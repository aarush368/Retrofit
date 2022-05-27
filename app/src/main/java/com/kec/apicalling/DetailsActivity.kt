package com.kec.apicalling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val detailsWebView = findViewById<WebView>(R.id.detailsWebView)
        val url = intent.getStringExtra("URL")
        if(url != null){
            detailsWebView.settings.userAgentString = "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3"
            detailsWebView.settings.javaScriptEnabled = true
            detailsWebView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    val progressBar : ProgressBar = findViewById(R.id.progressBar)
                    progressBar.visibility = View.GONE
                    detailsWebView.visibility = View.VISIBLE

                }
            }
            detailsWebView.loadUrl(url)
        }
    }
}