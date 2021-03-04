package com.guru.testnyarticles

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {


    companion object {

        val BASE_URL = "https://api.nytimes.com/"
        val API_KEY ="13p524VAC3GDqvRZVAPmNRqbLDajal29"

        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(myHttpClient())
                    .build()

        }
        class MyInterceptor : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {

                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url()

                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("api-key", API_KEY)
                        .build()

                val request = originalRequest.newBuilder().url(url).build()
                return chain.proceed(request)
            }
        }
        fun myHttpClient(): OkHttpClient {
            val builder = OkHttpClient().newBuilder()
                    .addInterceptor(MyInterceptor())
            return builder.build()
        }
    }

}