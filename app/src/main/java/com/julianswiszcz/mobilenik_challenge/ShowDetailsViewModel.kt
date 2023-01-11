package com.julianswiszcz.mobilenik_challenge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

class ShowDetailsViewModel(private val repository: ShowsRepository) : ViewModel() {
    private val _showId = MutableLiveData<Int>()
    private val showId: LiveData<Int>
        get() = _showId

    val show: LiveData<Show> = showId.switchMap {
        repository.getShowById(it)
    }

    fun setShowId(id: Int) {
        _showId.value = id
    }
}
