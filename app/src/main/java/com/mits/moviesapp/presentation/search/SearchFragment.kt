package com.mits.moviesapp.presentation.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mits.moviesapp.common.Constants.API_KEY
import com.mits.moviesapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        initAdapter()

        lifecycleScope.launch {
            viewModel.searchState
                .collectLatest {
                    isLoading = it.isLoading
                    adapter.submitList(it.mediaList.toList())
                    binding.searchProgressBar.isVisible = it.isLoading
                    binding.searchErrorTv.text = it.error
                }
        }

//        binding.searchRv.setOnScrollChangeListener { view, i, i2, i3, i4 ->
//            if (!binding.searchRv.canScrollVertically(1)) {
//                if (!isLoading) {
//                    viewModel.searchMediaItems(API_KEY, viewModel.editable, viewModel.searchPage)
//                }
//            }
//        }

        binding.searchInput.editText?.addTextChangedListener {
//            viewModel.editable = it.toString()
            viewModel.searchMediaItems(API_KEY, it.toString(), 1)
        }

        return binding.root
    }

    private fun initAdapter() {
        binding.searchRv.layoutManager = GridLayoutManager(context, 3)
        adapter = SearchAdapter()
        binding.searchRv.adapter = adapter
    }

}