package com.hypheno.imageloader.model.repository

import androidx.lifecycle.LiveData
import com.hypheno.imageloader.model.dataclass.Photo

interface PhotoRepository {

    val photos : LiveData<List<Photo>>

    suspend fun getPhotos(
        tag : String
    )
}