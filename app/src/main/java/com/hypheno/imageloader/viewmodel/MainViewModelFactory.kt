package com.hypheno.imageloader.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hypheno.imageloader.model.repository.PhotoRepository

class MainViewModelFactory(
    private val photoRepository: PhotoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(photoRepository) as T
    }

}