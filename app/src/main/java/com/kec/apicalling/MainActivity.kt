package com.kec.apicalling

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.littlemango.stacklayoutmanager.StackLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){
    lateinit var mAdapter: NewsListAdapter
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1
    val TAG = "MainActivity"
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544~3347511713",
            adRequest,
            object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError?.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })


        mAdapter = NewsListAdapter(this@MainActivity,articles)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = mAdapter
//      recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.BOTTOM_TO_TOP)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(2000)
        layoutManager.setItemChangedListener(object  : StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
               val contain : ConstraintLayout = findViewById(R.id.container)
                contain.setBackgroundColor(Color.parseColor(ColorPicker.getColor()))
                Log.d(TAG,"First visiblr item ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG,"Total Copunt ${layoutManager.itemCount}")
                val count = layoutManager.itemCount
                if(totalResults > count && layoutManager.getFirstVisibleItemPosition() >= count-5){
                    pageNum++;
                    getNews()
                }
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this@MainActivity)
                }else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.")
                }
            }

        })
        recyclerView.layoutManager = layoutManager
        getNews()
    }

    private fun getNews() {
        val news : Call<News> = NewsService.newsInstance.getHeadLines("in",pageNum)
        news.enqueue(object : Callback<News> {

            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news : News? = response.body()
                if(news != null){
                    Log.d("Wao",news.toString())
                    articles.addAll(news.articles)
                    mAdapter.notifyDataSetChanged()
                    totalResults = news.totalResults

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("CHECK","Api is not working dude")
            }
        })
    }
}