package com.botaoap.appnews.ui.feature.newslist.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withCreated
import com.botaoap.appnews.R
import com.botaoap.appnews.customview.DebtSupportModel
import com.botaoap.appnews.customview.SectionHideModel
import com.botaoap.appnews.databinding.FragmentNewsListBinding
import com.botaoap.appnews.ui.MainActivityListener
import com.botaoap.appnews.ui.extension.collapse
import com.botaoap.appnews.ui.extension.expand
import com.botaoap.appnews.ui.feature.newsdetail.view.NewsDetailFragment
import com.botaoap.appnews.ui.feature.newslist.adapter.NewsListAdapter
import com.botaoap.appnews.ui.feature.newslist.uistate.NewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.RefreshNewsListUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.SelectFilterUIState
import com.botaoap.appnews.ui.feature.newslist.uistate.ShowFooterUIState
import com.botaoap.appnews.ui.feature.newslist.viewmodel.NewsListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsListFragment : Fragment() {

    private lateinit var mainActivityListener: MainActivityListener

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!

    private val newsListViewModel: NewsListViewModel by viewModel()

    private val adapter: NewsListAdapter by lazy {
        NewsListAdapter(
            onClick = { data ->
                mainActivityListener.startFragment(
                    NewsDetailFragment.newInstance(data = data),
                    "NewsDetailFragment"
                )
            }
        )
    }

    companion object {
        fun newInstance() = NewsListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context as MainActivityListener
        mainActivityListener = context
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
                newsListViewModel.getNewsList()
            }
        }

        newsListViewModel.newsListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NewsListUIState.Loading -> adapter.submitList(state.data)

                is NewsListUIState.Success -> adapter.submitList(state.data.articles)

                is NewsListUIState.Empty -> adapter.submitList(state.data)

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

        newsListViewModel.showFooterState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ShowFooterUIState.Hidde -> binding.dsfLnoHomeOffersDebtFooter.visibility = View.GONE
                ShowFooterUIState.Invisible -> binding.dsfLnoHomeOffersDebtFooter.visibility = View.GONE
                ShowFooterUIState.Show -> {
                    lifecycleScope.launch {
                        binding.dsfLnoHomeOffersDebtFooter.apply {
                            visibility = View.VISIBLE
                            initView(
                                data = DebtSupportModel(
                                    title = "Saiba mais",
                                    description = "Faltam R\$ 300,00 para receber seu auxílio. Pague suas dívidas para completar e receber!",
                                    progressPercentage = 50,
                                    isOpen = false,
                                    sectionHide = SectionHideModel(
                                        title = "Auxílio Dívida - A Serasa paga uma dívida para você!",
                                        description = "Pague uma ou mais dívidas cujo valor total seja <b>igual ou maior que R\$ 300,00</b> e aproveite esse empurrãozinho para melhorar seu Score!"
                                    )
                                )
                            )
                        }
                    }
                }
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