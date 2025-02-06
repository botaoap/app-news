package com.botaoap.appnews.ui.feature.newslist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.botaoap.appnews.databinding.ItemNewsListCardBinding

class NewsListAdapter : ListAdapter<String, NewsListViewHolder>(NewsListDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsListViewHolder(
            ItemNewsListCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        getItem(position).let { item ->
            holder.bind(item)
        }
    }
}

class NewsListViewHolder(binding: ItemNewsListCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: String) {
        // TODO: implement glide for image
        // TODO: implement dynamic text for title
        // TODO: implement dynamic text for description
    }
}

class NewsListDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

}