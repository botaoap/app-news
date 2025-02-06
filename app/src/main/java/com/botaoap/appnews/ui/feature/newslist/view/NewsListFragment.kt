package com.botaoap.appnews.ui.feature.newslist.view

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.botaoap.appnews.R
import com.botaoap.appnews.databinding.FragmentNewsListBinding
import com.botaoap.appnews.ui.feature.newslist.adapter.NewsListAdapter
import com.botaoap.appnews.ui.feature.newslist.viewmodel.NewsListViewModel

class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    companion object {
        fun newInstance() = NewsListFragment()
    }

    private val viewModel: NewsListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNewsListItems.adapter = adapter
        adapter.submitList(listOf("", "", "", "", "", "", "", ""))
    }
}