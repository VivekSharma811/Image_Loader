package com.hypheno.imageloader.model.network

import com.hypheno.imageloader.model.dataclass.SearchResult
import com.hypheno.imageloader.model.network.interceptor.ConnectivityInterceptor
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "22502ac2fc1bdef98c36a977eee8f868"
const val BASE_URL = "https://api.flickr.com/services/"

interface SearchApiService {

    companion object {
        operator fun invoke(
            //connectivityInterceptor: ConnectivityInterceptor
        ) : SearchApiService {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                //.addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SearchApiService::class.java)
        }
    }

    @GET("rest")
    fun getImageData(
        @Query("method") method : String,
        @Query("format") format : String,
        @Query("nojsoncallback") jsonCallback : Int,
        @Query("tags") tags : String
    ) : Single<SearchResult>

}