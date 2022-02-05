package com.scan.ngwamovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.scan.ngwamovies.databinding.MediaItemBinding
import com.scan.ngwamovies.databinding.NetworkItemBinding
import com.scan.ngwamovies.model.MediaItem
import com.scan.ngwamovies.utils.NetworkState

class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val MEDIA_TYPE = 1
    private val ERROR_TYPE = 2
    private var networkState: NetworkState? = null
    private var mediaItems: List<MediaItem>? = null
    fun setNetworkState(networkState: NetworkState) {
        this.networkState = networkState
    }

    fun setDataList(articleList: List<MediaItem>) {
        this.mediaItems = articleList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MEDIA_TYPE) {
            ArticleItemViewHolder.create(parent)
        } else {
            NetworkStateViewHolder.create(parent)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            MEDIA_TYPE -> (holder as ArticleItemViewHolder).bind(mediaItems!![position])
            ERROR_TYPE -> (holder as NetworkStateViewHolder).bind(networkState!!)
        }
    }

    override fun getItemCount(): Int {
        return if (mediaItems != null) mediaItems!!.size
        else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            ERROR_TYPE
        } else {
            MEDIA_TYPE
        }

    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    class ArticleItemViewHolder(private val view: MediaItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(item: MediaItem) {
            view.item = item

        }

        companion object {
            fun create(parent: ViewGroup): ArticleItemViewHolder {

                return ArticleItemViewHolder(
                    MediaItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    class NetworkStateViewHolder(private val view: NetworkItemBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(networkState: NetworkState) {
            view.item = networkState
        }

        companion object {
            fun create(parent: ViewGroup): NetworkStateViewHolder {

                return NetworkStateViewHolder(
                    NetworkItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}