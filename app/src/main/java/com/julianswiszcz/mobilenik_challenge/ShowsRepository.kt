package com.julianswiszcz.mobilenik_challenge

class ShowsRepository constructor(private val retrofit: APIService) {

    suspend fun getShows(query: String) = retrofit.getShows(query)

}
