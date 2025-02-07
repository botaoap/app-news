package com.botaoap.appnews.ui.feature.newslist.adapter

import com.botaoap.appnews.databinding.ItemNewsListLoadingBinding
import com.botaoap.appnews.domain.model.ACNewsListModel

class NewsListLoadingViewHolder(private val binding: ItemNewsListLoadingBinding) : NewsListViewHolder(binding) {
    override fun bind(data: ACNewsListModel) {
        binding.sflItemListLoading.startShimmer()
    }
}