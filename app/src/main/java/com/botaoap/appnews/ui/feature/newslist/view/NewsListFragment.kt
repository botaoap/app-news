package com.botaoap.appnews.ui.feature.newslist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withCreated
import com.botaoap.appnews.R
import com.botaoap.appnews.data.contants.Constants
import com.botaoap.appnews.databinding.FragmentNewsListBinding
import com.botaoap.appnews.ui.feature.newslist.adapter.NewsListAdapter
import com.botaoap.appnews.ui.feature.newslist.uistate.NewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.RefreshNewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.SelectFilterUIState
import com.botaoap.appnews.ui.feature.newslist.viewmodel.NewsListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsListFragment : Fragment(R.layout.fragment_news_list) {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val newsListViewModel: NewsListViewModel by viewModel()

    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter()
    }

    companion object {
        fun newInstance() = NewsListFragment()
    }

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

        initObservables()
        swipeRefresh()
        setupFilter()
        binding.rvNewsListItems.adapter = adapter
    }

    private fun initObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.withCreated {
                newsListViewModel.selectFilter(
                    sources = Constants.Path.BBC_NEWS,
                    country = null
                )
            }
        }

        newsListViewModel.newsListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NewsListUIState.Loading -> adapter.submitList(state.data)

                is NewsListUIState.Success -> {
                    adapter.submitList(state.data.articles)
                }

                is NewsListUIState.Error -> adapter.submitList(state.data)
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsListViewModel.refreshState.collect { state ->
                    when (state) {
                        RefreshNewsListUIState.Disable ->
                            binding.srlNewsListRefresh.isEnabled = false

                        RefreshNewsListUIState.Enable -> {
                            binding.srlNewsListRefresh.isEnabled = true
                        }
                    }
                }
            }
        }

        newsListViewModel.selectFilterState.observe(viewLifecycleOwner) { state ->
            if (state is SelectFilterUIState.Selected) {
                newsListViewModel.getNewsList()
            }
        }
    }

    private fun swipeRefresh() {
        binding.srlNewsListRefresh.apply {
            setOnRefreshListener {
                newsListViewModel.getNewsList()
                binding.srlNewsListRefresh.isRefreshing = false
            }
        }
    }

    private fun setupFilter() {
        binding.ivNewsListFilter.setOnClickListener {
            NewsListFilterDialog(requireActivity()).apply {
                show { sources, country ->
                    dismiss()
                    newsListViewModel.selectFilter(
                        sources = sources,
                        country = country
                    )
                }
            }
        }
    }
}