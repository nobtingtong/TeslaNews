package com.scg.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.scg.core.di.CoreModuleDependencies
import com.scg.core.ui.widget.EndlessRecyclerOnScrollListener
import com.scg.core.viewstate.Status
import com.scg.news.databinding.FragmentNewsBinding
import com.scg.news.di.DaggerNewsComponent
import com.scg.news.viewmodel.NewsViewModel
import com.scg.test.R
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null

    @Inject
    lateinit var newsViewModel: NewsViewModel

    private val newsAdapter = NewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCoreDependentInjection()
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObservable()
        initListener()

        newsViewModel.getTeslaNews()
    }

    private fun initView() {
        _binding?.newsRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsAdapter
        }
    }

    private fun initListener() {
        _binding?.newsRecyclerView?.addOnScrollListener(object :
            EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                newsViewModel.getTeslaNews()
            }
        })

        _binding?.edtSearch?.doOnTextChanged { text, _, _, _ ->
            newsAdapter.filter.filter(text)
        }

        newsAdapter.onItemClick = {
            val bundle = Bundle().apply {
                putString("title", it.title)
                putString("description", it.description)
                putString("date", it.publishedAt)
                putString("imageUrl", it.urlToImage)
            }
            findNavController().navigate(R.id.nav_graph_detail, bundle)
        }
    }

    private fun initObservable() {
        newsViewModel.dataState.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.articles?.also { listArticle ->
                        newsAdapter.updateData(listArticle)
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Something when wrong", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    // handle on loading view
                }
            }
        }
    }

    private fun initCoreDependentInjection() {

        val coreModuleDependencies = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            CoreModuleDependencies::class.java
        )

        DaggerNewsComponent.factory().create(
            coreModuleDependencies = coreModuleDependencies,
            application = requireActivity().application,
            fragment = this
        ).inject(this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}