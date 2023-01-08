package com.julianswiszcz.mobilenik_challenge

class ShowsRepository constructor(private val retrofit: APIService) {

    suspend fun getShows(query: String) = retrofit.getShows(query)

    suspend fun getShows2(query: String) : NetworkState<ShowsResponse> {
        val response = retrofit.getShows(query)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
}
