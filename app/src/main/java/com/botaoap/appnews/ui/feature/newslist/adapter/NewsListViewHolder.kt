package com.botaoap.appnews.ui.feature.newslist.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.botaoap.appnews.domain.model.ACNewsListModel

abstract class NewsListViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(data: ACNewsListModel)
}