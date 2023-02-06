package com.example.marveluniverse.util

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.marveluniverse.R
import java.math.BigInteger
import java.security.MessageDigest

fun String.toMd5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun prepareImageURL(path: String, variant: String, extension: String): String {
    return "${path}/${variant}.${extension}"
}

fun ImageView.loadImage(
    uri: String?,
    circle: Boolean = false
) {

    var options = RequestOptions()
        .placeholder(ColorDrawable(ContextCompat.getColor(this.context, R.color.gray_300)))

    if (circle) {
        options = options.circleCrop()
    }

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}