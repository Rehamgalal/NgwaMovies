package com.scan.ngwamovies.model

import android.os.Parcelable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.scan.ngwamovies.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaItem(val id:Int, val type:String, val url:String, val name:String):Parcelable
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, item: String) {
    Glide.with(view.context)
        .load(item).centerCrop()
        .placeholder(R.drawable.pdf)
        .into(view) }