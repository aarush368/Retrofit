package com.kec.apicalling

data class News(
    val articles: List<Article>,
    val totalResults: Int
)