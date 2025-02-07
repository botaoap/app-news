package com.botaoap.appnews.ui.feature.newslist.view

import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.botaoap.appnews.R
import com.botaoap.appnews.data.contants.Constants
import com.botaoap.appnews.databinding.DialogNewsListFilterBinding

class NewsListFilterDialog(private val context: Context) {

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
                onClick.invoke(Constants.Path.BBC_NEWS, null)
            } else {
                onClick.invoke(null, Constants.Path.COUNTRY_US)
            }
        }
        binding.mrbNewsListFilterPrimary.buttonTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))

        binding.mrbNewsListFilterSecondary.buttonTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.black))
    }
}