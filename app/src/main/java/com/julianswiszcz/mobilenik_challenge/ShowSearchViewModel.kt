package com.julianswiszcz.mobilenik_challenge

import android.util.Log
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
    val errorMessage = MutableLiveData<String>()
    val showsList = MutableLiveData<ShowsResponse>()
    var job: Job? = null

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

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
