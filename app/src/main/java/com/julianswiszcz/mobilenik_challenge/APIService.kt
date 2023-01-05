package com.julianswiszcz.mobilenik_challenge

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    fun getShows(@Url query: String): Response<ShowsResponse>
}
