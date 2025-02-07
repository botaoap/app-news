package com.botaoap.appnews.ui.extension

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.botaoap.appnews.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.getImageFromLink(
    link: String,
    @DrawableRes imageError: Int,
    centerRadius: Float = 30f,
    strokeWidth: Float = 5f,
    widthLoading: Int = 100,
    heightLoading: Int = 100,
    @ColorRes colorLoading: Int = R.color.black,
) {
    val view = this
    val scale = resources.displayMetrics.density
    val newWidthInPx = (widthLoading * scale).toInt()
    val newHeightInPx = (heightLoading * scale).toInt()

    view.layoutParams.let {
        it.width = newWidthInPx
        it.height = newHeightInPx
        view.layoutParams = it
    }

    CircularProgressDrawable(context).let { circularProgressDrawable ->
        circularProgressDrawable.strokeWidth = strokeWidth
        circularProgressDrawable.centerRadius = centerRadius
        circularProgressDrawable.setColorSchemeColors(
            ContextCompat.getColor(context, colorLoading)
        )
        circularProgressDrawable.start()

        Glide.with(context)
            .load(link)
            .placeholder(circularProgressDrawable)
            .error(imageError)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    val padding = 24
                    (padding * Resources.getSystem().displayMetrics.density).toInt().let { newPadding ->
                        setPadding(newPadding,newPadding,newPadding,newPadding)
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    view.layoutParams.let {
                        it.width = ViewGroup.LayoutParams.WRAP_CONTENT
                        it.height = ViewGroup.LayoutParams.WRAP_CONTENT
                        view.layoutParams = it
                    }
                    return false
                }

            })
            .into(this)
    }
}