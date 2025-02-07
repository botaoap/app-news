package com.botaoap.appnews.ui.feature.newsdetail.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.withCreated
import com.botaoap.appnews.databinding.FragmentNewsDetailBinding
import com.botaoap.appnews.domain.model.ArticlesModel
import com.botaoap.appnews.ui.extension.loadImageFromCache
import com.botaoap.appnews.ui.feature.newsdetail.uistate.NewsDetailUIState
import com.botaoap.appnews.ui.feature.newsdetail.viewmodel.NewsDetailViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NewsDetailFragment : Fragment() {

    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    private val data: ArticlesModel by lazy { arguments?.getParcelable(ARGUMENT_DATA_DETAIL)!! }

    private val detailViewModel: NewsDetailViewModel by viewModel { parametersOf(data) }

    companion object {
        const val ARGUMENT_DATA_DETAIL = "argument_data_detail"

        fun newInstance(
            data: ArticlesModel
        ) = NewsDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARGUMENT_DATA_DETAIL, data)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservables()
    }

    private fun initObservables() {
        lifecycleScope.launch {
            viewLifecycleOwner.withCreated {
                detailViewModel.initView()
            }
        }

        detailViewModel.newsDetailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NewsDetailUIState.ShowView -> {
                    showView(state.data)
                }
            }
        }
    }

    private fun showView(data: ArticlesModel) {
        setupBackButton()
        setupExternalButton(data.url)
        setupImage(data.urlToImage)
        setupTile(data.title)
        setupDescription(data.description)
        setupAuthor(data.author)
        setupDate(data.publishedAt)
        setupContent(data.content)
    }

    private fun setupBackButton() {
        binding.ibNewsDetailBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupExternalButton(data: String) {
        binding.ivNewsDetailOpenExternal.setOnClickListener {
            openBrowser(data)
        }
    }

    private fun setupImage(data: String?) {
        binding.ivNewsDetailImage.apply {
            data?.let {
                visibility = View.VISIBLE
                loadImageFromCache(it)
            } ?: run {
                visibility = View.GONE
            }
        }
    }

    private fun setupTile(data: String) {
        binding.tvNewsDetailTitle.text = data
    }

    private fun setupDescription(data: String?) {
        binding.tvNewsDetailDescription.apply {
            data?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                visibility = View.GONE
            }
        }
    }

    private fun setupAuthor(data: String?) {
        binding.tvNewsDetailAuthor.apply {
            data?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                binding.flowNewsDetailAuthorContainer.setHorizontalBias(1f)
                visibility = View.GONE
            }
        }
    }

    private fun setupDate(data: String?) {
        binding.tvNewsDetailDate.apply {
            data?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                binding.flowNewsDetailAuthorContainer.setHorizontalBias(0f)
                visibility = View.GONE
            }
        }
    }

    private fun setupContent(data: String?) {
        binding.tvNewsDetailContent.apply {
            data?.let {
                visibility = View.VISIBLE
                text = it
            } ?: run {
                visibility = View.GONE
            }
        }
    }

    private fun openBrowser(data: String) {
        data.let { siteAction ->
            requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(siteAction)))
        }
    }
}