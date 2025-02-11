package com.botaoap.appnews.ui.extension

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation

fun View.expand() {
    val animation = expandAction(this)
    this.startAnimation(animation)
}

private fun expandAction(view: View): Animation {
    with(view) {
        measure(
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED)
        )
        val actualHeight = measuredHeight
        layoutParams.height = 1
        visibility = View.VISIBLE
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                layoutParams.height =
                    if (interpolatedTime == 1f) {
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    } else {
                        (actualHeight * interpolatedTime).toInt()
                    }
                requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        animation.duration = (actualHeight / context.resources.displayMetrics.density).toLong()
        startAnimation(animation)
        return animation
    }
}

fun View.collapse() {
    with(this) {
        val actualHeight = measuredHeight
        layoutParams.height = 1
        val animation: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    visibility = View.GONE
                } else {
                    layoutParams.height = actualHeight - (actualHeight * interpolatedTime).toInt()
                    requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        animation.duration = (actualHeight / context.resources.displayMetrics.density).toLong()
        startAnimation(animation)
    }
}