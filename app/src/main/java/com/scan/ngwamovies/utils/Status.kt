package com.scan.ngwamovies.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.scan.ngwamovies.model.MediaItem

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(val status: Status,
                                            val message: String? = null) {

    companion object {
        val LOADED = NetworkState(Status.SUCCESS)
        val LOADING = NetworkState(Status.LOADING)
        fun error(msg: String?) = NetworkState(Status.ERROR, msg)
    }

}
@BindingAdapter("visibility")
fun showBar(view: ProgressBar, item: NetworkState) {
    if (item.status.equals(Status.LOADING)) view.visibility = View.VISIBLE
    else view.visibility = View.GONE
}
@BindingAdapter("visibility")
fun setText(view: TextView, item: NetworkState) {
    if (item.status.equals(Status.ERROR)){ view.visibility = View.VISIBLE
        view.text = item.message
    }
    else View.GONE
}
