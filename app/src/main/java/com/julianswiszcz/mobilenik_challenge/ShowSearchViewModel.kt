package com.julianswiszcz.mobilenik_challenge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShowSearchViewModel(private val repository: ShowsRepository) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val showsList = MutableLiveData<List<ShowsResponse>>()
    var job: Job? = null
    private val _showId = MutableLiveData<Int>()
    val showId: LiveData<Int>
        get() = _showId

    val show = MutableLiveData<ShowsResponse>()

    val loading = MutableLiveData(false)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllShows(query: String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getShows(query)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    showsList.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun getShowById(id: Int) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getShowById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    show.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun setShowId(id: Int) {
        _showId.value = id
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
