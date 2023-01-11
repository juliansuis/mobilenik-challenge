package com.julianswiszcz.mobilenik_challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory constructor(private val repository: ShowsRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ShowSearchViewModel::class.java) ->
                ShowSearchViewModel(this.repository) as T
            modelClass.isAssignableFrom(ShowDetailsViewModel::class.java) ->
                ShowDetailsViewModel(this.repository) as T
            else -> throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
