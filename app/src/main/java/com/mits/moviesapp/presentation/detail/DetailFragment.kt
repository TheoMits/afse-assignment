package com.mits.moviesapp.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.mits.moviesapp.R
import com.mits.moviesapp.common.Constants
import com.mits.moviesapp.common.Constants.API_KEY
import com.mits.moviesapp.databinding.FragmentDetailBinding
import com.mits.moviesapp.domain.model.MediaDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getMediaItemById(API_KEY, args.mediaId, args.mediaType)

        lifecycleScope.launch {
            viewModel.detailState.collectLatest {
                it.detailItem?.let { it1 -> updateUi(it1) }
                binding.detailProgressBar.isVisible = it.isLoading
                binding.detailErrorMsg.text = it.error
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        return binding.root
    }

    private fun updateUi(mediaDetail: MediaDetail) {
        Glide.with(this).load("${Constants.IMAGES_BASE_URL}${mediaDetail.backDropPath}")
            .placeholder(R.drawable.media_placeholder).into(binding.detailBackDropImg)
        Glide.with(this).load("${Constants.IMAGES_BASE_URL}${mediaDetail.posterPath}")
            .placeholder(R.drawable.media_placeholder).into(binding.detailPosterImg)
        binding.detailTitle.text = mediaDetail.title
        if (mediaDetail.genres.isNotEmpty()) binding.detailGenre.text = mediaDetail.genres[0]
        binding.detailSummary.text = mediaDetail.summary
        binding.topAppBar.title = mediaDetail.title
    }

}