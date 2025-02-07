package com.botaoap.appnews.ui.feature.newslist.adapter

import com.botaoap.appnews.databinding.ItemNewsListEmptyBinding
import com.botaoap.appnews.databinding.ItemNewsListErrorBinding
import com.botaoap.appnews.domain.model.ACNewsListModel

class NewsListEmptyViewHolder(private val binding: ItemNewsListEmptyBinding) : NewsListViewHolder(binding) {
    override fun bind(data: ACNewsListModel) {}
}