package com.guru.testnyarticles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel : ViewModel(){
    lateinit var recyclerListData: MutableLiveData<ArticleResponse>
    lateinit var recyclerViewAdapter : RecyclerViewAdapter

    lateinit var horizontal_recyclerListData: MutableLiveData<HorizontalArticleResponse>
    lateinit var horizontal_recyclerViewAdapter : HorizontalRecyclerViewAdapter


    init {
        recyclerListData = MutableLiveData()
        recyclerViewAdapter = RecyclerViewAdapter()

        horizontal_recyclerListData = MutableLiveData()
        horizontal_recyclerViewAdapter = HorizontalRecyclerViewAdapter()
    }

    fun getAdapter():  RecyclerViewAdapter{
        return recyclerViewAdapter
    }

    fun getHorizontalAdapter():  HorizontalRecyclerViewAdapter{
        return horizontal_recyclerViewAdapter
    }

    fun setAdapterData(data: ArrayList<RecyclerData>) {
        recyclerViewAdapter.setDataList(data)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun setHorizontalAdapterData(data: ArrayList<HorizontalRecyclerData>) {
        horizontal_recyclerViewAdapter.setDataList(data)
        horizontal_recyclerViewAdapter.notifyDataSetChanged()
    }

    fun getRecyclerListDataObserver(): MutableLiveData<ArticleResponse> {
        return recyclerListData
    }
    fun getHorizontalRecyclerListDataObserver(): MutableLiveData<HorizontalArticleResponse> {
        return horizontal_recyclerListData
    }

    fun makeAPICall(input : String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getDataFromAPI(input)
        call.enqueue(object : Callback<ArticleResponse>{
            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                if(response.isSuccessful){
                    recyclerListData.postValue(response.body())
                } else {
                    recyclerListData.postValue(null)
                }
            }
        })
    }
    fun makeHorizontalAPICall() {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        val call = retroInstance.getHorizontalDataFromAPI()
        call.enqueue(object : Callback<HorizontalArticleResponse>{
            override fun onFailure(call: Call<HorizontalArticleResponse>, t: Throwable) {
                horizontal_recyclerListData.postValue(null)
            }

            override fun onResponse(call: Call<HorizontalArticleResponse>, response: Response<HorizontalArticleResponse>) {
                if(response.isSuccessful){
                    horizontal_recyclerListData.postValue(response.body())
                } else {
                    horizontal_recyclerListData.postValue(null)
                }
            }
        })
    }
}