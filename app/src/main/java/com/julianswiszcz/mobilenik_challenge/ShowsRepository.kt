package com.julianswiszcz.mobilenik_challenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsRepository {

    fun getShows(query: String): LiveData<List<ShowsResponse>> {
        val mResponse = MutableLiveData<List<ShowsResponse>>()
        APIService.getInstance().getShows(query).enqueue(
            object : Callback<List<ShowsResponse>> {
                override fun onResponse(
                    call: Call<List<ShowsResponse>>,
                    response: Response<List<ShowsResponse>>
                ) {
                    mResponse.value = response.body()
                }

                override fun onFailure(call: Call<List<ShowsResponse>>, t: Throwable) {
                    Log.e("JULI", t.message.orEmpty())
                }
            }
        )
        return mResponse
    }

    fun getShowById(id: Int): LiveData<Show> {
        val mResponse = MutableLiveData<Show>()
        APIService.getInstance().getShowById(id).enqueue(
            object : Callback<Show> {
                override fun onResponse(
                    call: Call<Show>,
                    response: Response<Show>
                ) {
                    mResponse.value = response.body()
                }

                override fun onFailure(call: Call<Show>, t: Throwable) {
                    Log.e("JULI", t.message.orEmpty())
                }
            }
        )
        return mResponse
    }

    fun getShowEpisodes(id: Int): LiveData<List<Show>> {
        val mResponse = MutableLiveData<List<Show>>()
        APIService.getInstance().getShowEpisodes(id).enqueue(
            object : Callback<List<Show>> {
                override fun onResponse(
                    call: Call<List<Show>>,
                    response: Response<List<Show>>
                ) {
                    mResponse.value = response.body()
                }

                override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                    Log.e("JULI", t.message.orEmpty())
                }
            }
        )
        return mResponse
    }

}
