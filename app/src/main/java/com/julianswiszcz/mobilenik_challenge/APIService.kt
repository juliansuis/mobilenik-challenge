package com.julianswiszcz.mobilenik_challenge

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("search/shows")
    fun getShows(@Query("q") query: String): Call<List<ShowsResponse>>

    @GET("shows/{id}")
    fun getShowById(@Path("id") id: Int): Call<Show>

    companion object {
        private var retrofitService: APIService? = null
        fun getInstance(): APIService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.tvmaze.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(APIService::class.java)
            }
            return retrofitService!!
        }
    }
}
