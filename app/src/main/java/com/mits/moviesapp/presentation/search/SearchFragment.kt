package com.mits.moviesapp.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mits.moviesapp.R
import com.mits.moviesapp.common.enums.MediaType
import com.mits.moviesapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchAdapter.MediaItemListener {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels()

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
        updateIfOrientationChanged()

        lifecycleScope.launch {
            viewModel.searchState
                .collect {
                    updateUi(it)
                }
        }

        binding.searchRv.setOnScrollChangeListener { _, _, _, _, _ ->
            if (!binding.searchRv.canScrollVertically(1)) {
                viewModel.onRecyclerScroll()
            }
        }

        binding.searchInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(query: Editable?) {
                viewModel.onSearchTextChanged(query.toString())
            }
        })

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (viewModel.isWatchListEnabled) {
                viewModel.onWatchListEnabled()
                menuItem.setIcon(R.drawable.tv_disabled)
                binding.searchInput.editText?.setText(viewModel.query)
            } else {
                viewModel.onWatchListDisabled()
                menuItem.setIcon(R.drawable.tv_enabled)
                binding.searchInput.editText?.setText(viewModel.query)
            }
            return@setOnMenuItemClickListener true
        }
        return binding.root
    }

    private fun initAdapter() {
        binding.searchRv.layoutManager = GridLayoutManager(context, 3)
        adapter = SearchAdapter(this)
        binding.searchRv.adapter = adapter
    }

    private fun updateIfOrientationChanged() {
        binding.searchInput.editText?.setText(viewModel.query)
        binding.topAppBar.menu.getItem(0).setIcon(viewModel.menuIcon)
        if (viewModel.isWatchListEnabled) viewModel.getWatchList()
    }

    private fun updateUi(searchState: SearchState) {
        if (!searchState.isLoading) adapter.submitList(searchState.searchList.toList())
        binding.searchProgressBar.isVisible = searchState.isLoading
        binding.searchErrorTv.text = searchState.error
    }

    override fun onItemClicked(mediaId: Int, mediaType: MediaType) {
        viewModel.onItemClicked()
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                mediaId,
                mediaType
            )
        )
    }

}