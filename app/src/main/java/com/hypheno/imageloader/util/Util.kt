package com.hypheno.imageloader.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hypheno.imageloader.R

fun getProgressDrawable(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri : String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .into(this)
}

@BindingAdapter("farmId", "serverId", "id", "secret")
fun loadImage(view : ImageView, farmId : String, serverId : String, id : String, secret : String) {
    val url = "https://farm${farmId}.staticflickr.com/${serverId}/${id}_${secret}.jpg"
    view.loadImage(url, getProgressDrawable(view.context))
}