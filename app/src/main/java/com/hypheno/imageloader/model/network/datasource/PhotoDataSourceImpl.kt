package com.hypheno.imageloader.model.network.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hypheno.imageloader.model.dataclass.Photo
import com.hypheno.imageloader.model.dataclass.SearchResult
import com.hypheno.imageloader.model.network.SearchApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PhotoDataSourceImpl(
    private val searchApiService: SearchApiService
) : PhotoDataSource {

    private val _photos = MutableLiveData<List<Photo>>()

    override val photos: LiveData<List<Photo>>
        get() = _photos

    override suspend fun fetchPhotos(tag: String) {
        CompositeDisposable().add(
            searchApiService.getImageData("flickr.photos.search", "json", 1, tag)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResult>() {
                    override fun onSuccess(t: SearchResult) {
                        _photos.postValue(t.photos.photo)
                    }

                    override fun onError(e: Throwable) {
                        Log.e("Error", e.toString())
                    }

                })
        )
    }
}