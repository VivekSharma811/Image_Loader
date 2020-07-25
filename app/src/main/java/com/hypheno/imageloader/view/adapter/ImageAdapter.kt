package com.hypheno.imageloader.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hypheno.imageloader.R
import com.hypheno.imageloader.databinding.ItemImageBinding
import com.hypheno.imageloader.model.dataclass.Photo

class ImageAdapter(val imageList : ArrayList<Photo>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(var view : ItemImageBinding) : RecyclerView.ViewHolder(view.root)

    fun updateImages(images : List<Photo>) {
        imageList.clear()
        imageList.addAll(images)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemImageBinding>(inflater, R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount() = imageList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.view.photo = imageList[position]
    }
}