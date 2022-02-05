package com.scan.ngwamovies.utils

import com.scan.ngwamovies.model.MediaItem

interface OnItemClicked {

    fun onClick(item: MediaItem)
}