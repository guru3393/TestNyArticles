package com.guru.testnyarticles

data class ArticleResponse ( val status:String,val copyright:String,val response:Response)
data class Response (val docs: ArrayList<RecyclerData>)
data class RecyclerData(val web_url: String, val source: String, val pub_date: String, val headline: HeadLine)
data class HeadLine(val main: String)
