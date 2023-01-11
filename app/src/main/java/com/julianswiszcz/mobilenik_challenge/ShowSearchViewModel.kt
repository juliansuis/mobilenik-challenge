package com.julianswiszcz.mobilenik_challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import retrofit2.Response

class ShowSearchViewModel(private val repository: ShowsRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    private val query: LiveData<String>
        get() = _query

    val showList: LiveData<List<ShowsResponse>> = query.switchMap {
         repository.getShows(it)
    }

    fun setQuery(query: String) {
        _query.value = query
    }
}
