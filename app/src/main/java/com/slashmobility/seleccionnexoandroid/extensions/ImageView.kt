package com.slashmobility.seleccionnexoandroid.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.slashmobility.seleccionnexoandroid.R

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-03
 */

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .placeholder(R.mipmap.placeholder)
        .error(R.mipmap.placeholder)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}