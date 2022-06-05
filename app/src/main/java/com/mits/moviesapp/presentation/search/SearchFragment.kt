package com.mits.moviesapp.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
        updateViews()

        // observe search state
        lifecycleScope.launch {
            viewModel.searchState
                .collect {
                    updateUiByState(it)
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
                viewModel.disableWatchList()
                binding.searchInput.editText?.setText(viewModel.query)
            } else {
                viewModel.enableWatchList()
                binding.searchInput.editText?.setText(viewModel.query)
            }
            return@setOnMenuItemClickListener true
        }

        viewModel.menuItemIcon.observe(viewLifecycleOwner, Observer {
            binding.topAppBar.menu.getItem(0).setIcon(it)
        })

        return binding.root
    }

    private fun initAdapter() {
        binding.searchRv.layoutManager = GridLayoutManager(context, 3)
        adapter = SearchAdapter(this)
        binding.searchRv.adapter = adapter
    }

    private fun updateViews() {
        binding.searchInput.editText?.setText(viewModel.query)
        viewModel.onUpdateView()
    }

    private fun updateUiByState(searchState: SearchState) {
        if (!searchState.isLoading) adapter.submitList(searchState.searchList.toList())
        binding.searchProgressBar.isVisible = searchState.isLoading
        binding.searchErrorTv.text = searchState.error
    }

    override fun onItemClicked(mediaId: Int, mediaType: MediaType) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailFragment(
                mediaId,
                mediaType
            )
        )
    }

}