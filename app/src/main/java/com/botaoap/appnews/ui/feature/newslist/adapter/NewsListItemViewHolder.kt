package com.botaoap.appnews.ui.feature.newslist.adapter

import android.view.View
import com.botaoap.appnews.R
import com.botaoap.appnews.databinding.ItemNewsListCardBinding
import com.botaoap.appnews.domain.model.ACNewsListModel
import com.botaoap.appnews.domain.model.ArticlesModel
import com.botaoap.appnews.ui.extension.getImageFromLink

class NewsListItemViewHolder(
    private val binding: ItemNewsListCardBinding,
    private val onClick: (data: ArticlesModel) -> Unit
) :
    NewsListViewHolder(binding) {

    override fun bind(data: ACNewsListModel) {
        with(data as ArticlesModel) {
            setupTitle(title)
            setupImage(urlToImage)
            setupAuthor(author)
            setupDate(publishedAt)
            setupClick(this)
        }
    }

    private fun setupTitle(data: String) {
        binding.tvItemCardTitle.text = data
    }

    private fun setupImage(data: String?) {
        binding.ivItemCardImage.getImageFromLink(
            link = data ?: "",
            imageError = R.drawable.ic_image_not_supported,
            centerRadius = 40f,
            strokeWidth = 10f
        )
    }

    private fun setupAuthor(data: String?) {
        binding.tvItemCardAuthor.apply {
            data?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                visibility = View.GONE
            }
        }
    }

    private fun setupDate(data: String?) {
        binding.tvItemCardDate.apply {
            data?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                visibility = View.GONE
            }
        }
    }

    private fun setupClick(data: ArticlesModel) {
        binding.mcvItemCardContainer.setOnClickListener {
            onClick.invoke(data)
        }
    }
}