package com.julianswiszcz.mobilenik_challenge

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ShowSearchViewModel(private val repository: ShowsRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val showsList = MutableLiveData<ShowsResponse>()
    var job: Job? = null

    val loading = MutableLiveData(false)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getAllShows(query: String) {
        viewModelScope.launch {
            when (val response = repository.getShows2(query)) {
                is NetworkState.Success -> {
                    showsList.postValue(response.data ?: ShowsResponse(emptyList()))
                }
                else -> {
                    Log.e("JULI", "network state error")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
