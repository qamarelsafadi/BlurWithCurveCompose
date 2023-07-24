package com.qamar.myapplication.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DynamicColor(val drawable: Int, context: Context) {
    private val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, drawable)
    private val palette: Palette = Palette.from(bitmap)
        .addFilter { _, hsl ->
            return@addFilter hsl[0] in 200.0..390.0
        }
        .generate()
    val imageDynamicColor = palette.dominantSwatch?.rgb?.let { Color(it) } ?: Color.Transparent
}

class DynamicColorOnline(drawable: String, context: Context, coroutineScope: CoroutineScope) {
    var imageDynamicColor = Color.Transparent

    init {
        coroutineScope.launch {
            val loader = ImageLoader(context)
            val req = ImageRequest.Builder(context)
                .data(drawable) // demo link
                .target { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    val palette: Palette? =
                        bitmap?.let {
                            Palette.from(it)
                                .addFilter { rgb, hsl ->
                                    return@addFilter hsl[0] in 200.0..390.0
                                }
                                .generate()
                        }

                    if (palette != null) {
                        imageDynamicColor =
                            palette.dominantSwatch?.rgb?.let { Color(it) } ?: Color.Transparent
                    }
                }
                .build()

            loader.enqueue(req)

        }
    }
}