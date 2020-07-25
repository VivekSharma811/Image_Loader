package com.hypheno.imageloader.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hypheno.imageloader.model.dataclass.Photo
import com.hypheno.imageloader.model.db.PhotoDao
import com.hypheno.imageloader.model.network.datasource.PhotoDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoRepositoryImpl(
    private val photoDao: PhotoDao,
    private val photoDataSource: PhotoDataSource
) : PhotoRepository {

    init {
        photoDataSource.photos.observeForever {
            persistFetchedData(it)
        }
        photoDao.getAllPhotos().observeForever {
            _photos.postValue(it)
        }
    }

    private val _photos = MutableLiveData<List<Photo>>()

    override val photos: LiveData<List<Photo>>
        get() = _photos

    override suspend fun getPhotos(tag : String) {
        emptyDb()
        initFetchFromApi(tag)
    }

    private fun emptyDb() {
        GlobalScope.launch(Dispatchers.IO) {
            photoDao.deleteAll()
        }
    }

    private fun persistFetchedData(photoList : List<Photo>) {
        GlobalScope.launch(Dispatchers.IO) {
            photoDao.insert(*photoList.toTypedArray())
        }
    }

    private suspend fun initFetchFromApi(tag : String) {
        if(true) {
            photoDataSource.fetchPhotos(tag)
        }
    }
}