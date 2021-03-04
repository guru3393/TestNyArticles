package com.guru.testnyarticles

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("svc/search/v2/articlesearch.json")
   fun getDataFromAPI(@Query("q")query : String):Call<ArticleResponse>

    @GET("svc/topstories/v2/arts.json")
    fun getHorizontalDataFromAPI():Call<HorizontalArticleResponse>
}