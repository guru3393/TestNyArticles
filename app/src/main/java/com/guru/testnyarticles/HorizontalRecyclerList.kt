package com.guru.testnyarticles

data class HorizontalArticleResponse ( val status:String,val copyright:String,val results: ArrayList<HorizontalRecyclerData>)
data class HorizontalRecyclerData(val title: String, val published_date: String, val multimedia: ArrayList<HorizontalMultimedia>)
data class HorizontalMultimedia(val url: String)
