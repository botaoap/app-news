package com.botaoap.appnews.ui.feature.newslist.adapter

import com.botaoap.appnews.R
import com.botaoap.appnews.databinding.ItemNewsListCardBinding
import com.botaoap.appnews.ui.extension.getImageFromLink

class NewsListItemViewHolder(private val binding: ItemNewsListCardBinding) :
    NewsListViewHolder(binding) {

    override fun bind(data: String) {
        // TODO: implement glide for image
        binding.ivItemCardImage.getImageFromLink(
            link = "https://ichef.bbci.co.uk/ace/branded_news/1200/cpsprodpb/be74/live/9a84f4d0-e486-11ef-90d6-551a5c070155.jpg",
            imageError = R.drawable.ic_launcher_foreground,
            centerRadius = 40f,
            strokeWidth = 10f
        )

        // TODO: implement dynamic text for title
        // TODO: implement dynamic text for description
        // TODO: implement click action
        binding.root.setOnClickListener {
            // TODO: goes to news detail
        }
    }
}