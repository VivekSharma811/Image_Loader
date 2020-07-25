package com.hypheno.imageloader.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.hypheno.imageloader.R
import com.hypheno.imageloader.model.dataclass.SearchResult
import com.hypheno.imageloader.model.network.SearchApiService
import com.hypheno.imageloader.model.network.interceptor.ConnectivityInterceptor
import com.hypheno.imageloader.view.adapter.ImageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.launch

class MainFragment : ScopedFragment() {

    private val imageAdapter = ImageAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageList.apply {
            layoutManager = GridLayoutManager(context, 4)
            adapter = imageAdapter
        }

        bindUi()
    }

    fun bindUi() = launch {
        CompositeDisposable().add(
            SearchApiService().getImageData("flickr.photos.search", "json", 1, "dogs")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<SearchResult>() {
                    override fun onSuccess(t: SearchResult) {
                        imageList.visibility = View.VISIBLE
                        imageAdapter.updateImages(t.photos.photo)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, "Error : ${e.toString()}", Toast.LENGTH_LONG).show()
                    }

                })
        )
    }
}