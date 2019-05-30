package cz.eman.kaalsample.infrastructure.file.image

import android.widget.ImageView


interface ImageLoader {

    fun load(url: String, imageView: ImageView, callback: (Boolean) -> Unit)

    fun load(url: String, imageView: ImageView, fadeEffect: Boolean = true)
}

