package com.julianswiszcz.mobilenik_challenge

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("search/shows")
    suspend fun getShows(@Query("q") query: String): Response<List<ShowsResponse>>

    @GET("shows/{id}")
    suspend fun getShowById(@Path("id") id: Int): Response<ShowsResponse>

    companion object {
        var retrofitService: APIService? = null
        fun getInstance(): APIService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.tvmaze.com/")
                    .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor {
                        Log.e("JULI", it)
                    }.apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }).build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(APIService::class.java)
            }
            return retrofitService!!
        }
    }
}
