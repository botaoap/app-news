package com.botaoap.appnews.ui.feature.newslist.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.botaoap.appnews.databinding.DialogNewsListFilterBinding

class NewsListFilterDialog(context: Context) {

    private val binding =
        DialogNewsListFilterBinding.inflate(LayoutInflater.from(context))

    private val dialog = Dialog(context)

    fun show(onClick: (sources: String?, country: String?) -> Unit) {
        dialog.window?.apply {
            setLayout(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        if (!dialog.isShowing) {
            dialog.setContentView(binding.root)
            dialog.show()
        }
        setupBorder()
        confirmChoose(onClick)
    }

    fun dismiss() {
        if (dialog.isShowing) dialog.dismiss()
    }

    private fun setupBorder() {
        binding.root.clipToOutline = true
        binding.root.background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 30f
            setColor(Color.WHITE)
        }
    }

    private fun confirmChoose(onClick: (sources: String?, country: String?) -> Unit) {
        binding.mbNewsListFilterConfirm.setOnClickListener {
            if (binding.mrbNewsListFilterPrimary.isChecked) {
                onClick.invoke("bbc-news", null)
            } else {
                onClick.invoke(null, "us")
            }
        }
    }
}