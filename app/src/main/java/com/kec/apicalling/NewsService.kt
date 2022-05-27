package com.kec.apicalling

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

///v2/top-headlines?country=in&category=science&apiKey=
const val BASE_URL = "https://newsapi.org/"
const val API_KEY =  "a2f658ea7b664d19b437a2424bef8bbc"

interface NewsInterface {

    @GET("v2/top-headlines?apiKey=$API_KEY")
    fun getHeadLines (@Query("country")country : String,@Query("page")page : Int) : Call<News>

}

object NewsService {
    var newsInstance : NewsInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }


}