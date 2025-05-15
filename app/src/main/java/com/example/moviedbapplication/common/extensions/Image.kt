package com.example.moviedbapplication.common.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviedbapplication.common.util.Constants

fun ImageView.loadImage(
    url: String?,
    isCircleCrop: Boolean = false,
    errorImage: Int? = null
) {
    val skipMemoryCache = RequestOptions().skipMemoryCache(false)

    val circleCrop = if (isCircleCrop) RequestOptions().circleCrop() else RequestOptions()

    Glide.with(context)
        .load(Constants.IMAGE_BASE_URL + url)
        .apply(skipMemoryCache)
        .error(errorImage)
        .apply(circleCrop)
        .into(this)
}