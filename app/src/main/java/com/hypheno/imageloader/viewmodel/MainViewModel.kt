package com.hypheno.imageloader.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hypheno.imageloader.model.dataclass.Photo
import com.hypheno.imageloader.model.repository.PhotoRepository

class MainViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photosList = MutableLiveData<List<Photo>>()

    val photosList : LiveData<List<Photo>>
        get() = _photosList

    init {
        photoRepository.photos.observeForever {
            _photosList.postValue(it)
        }
    }

    suspend fun getPhotos(tag : String) {
        photoRepository.getPhotos(tag)
    }

}