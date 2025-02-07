package com.botaoap.appnews.ui.feature.newslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.botaoap.appnews.databinding.ItemNewsListCardBinding
import com.botaoap.appnews.databinding.ItemNewsListErrorBinding
import com.botaoap.appnews.databinding.ItemNewsListLoadingBinding
import com.botaoap.appnews.domain.model.ACNewsListModel
import com.botaoap.appnews.domain.model.ArticlesModel
import com.botaoap.appnews.domain.model.NewsListErrorModel
import com.botaoap.appnews.domain.model.NewsListLoadingModel

class NewsListAdapter : ListAdapter<ACNewsListModel, NewsListViewHolder>(NewsListDiffUtil()) {

    companion object {
        private const val CARD_LOADING = 0
        private const val CARD_NEWS = 1
        private const val CARD_ERROR = 2
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is NewsListLoadingModel -> CARD_LOADING
            is ArticlesModel -> CARD_NEWS
            is NewsListErrorModel -> CARD_ERROR
            else -> CARD_ERROR
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            CARD_NEWS -> NewsListItemViewHolder(
                ItemNewsListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            )

            CARD_LOADING -> NewsListLoadingViewHolder(
                ItemNewsListLoadingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )

            CARD_ERROR -> NewsListErrorViewHolder(
                ItemNewsListErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )

            else -> NewsListErrorViewHolder(
                ItemNewsListErrorBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
            )
        }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        getItem(position).let { item ->
            when (getItemViewType(position)) {
                CARD_NEWS -> (holder as NewsListItemViewHolder).apply { bind(item) }
                CARD_LOADING -> (holder as NewsListLoadingViewHolder).apply { bind(item) }
                CARD_ERROR -> (holder as NewsListErrorViewHolder).apply { bind(item) }
                else -> (holder as NewsListErrorViewHolder).apply { bind(item) }
            }
        }
    }
}

class NewsListDiffUtil : DiffUtil.ItemCallback<ACNewsListModel>() {
    override fun areItemsTheSame(oldItem: ACNewsListModel, newItem: ACNewsListModel): Boolean =
        oldItem.status == newItem.status

    override fun areContentsTheSame(oldItem: ACNewsListModel, newItem: ACNewsListModel): Boolean =
        oldItem == newItem

}