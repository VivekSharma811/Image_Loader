package com.hypheno.imageloader.model.network.datasource

import androidx.lifecycle.LiveData
import com.hypheno.imageloader.model.dataclass.Photo

interface PhotoDataSource {

    val photos : LiveData<List<Photo>>

    suspend fun fetchPhotos(
        tag : String
    )
}