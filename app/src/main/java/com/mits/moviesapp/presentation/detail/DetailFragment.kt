package com.mits.moviesapp.presentation.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.mits.moviesapp.R
import com.mits.moviesapp.common.Constants
import com.mits.moviesapp.common.Constants.API_KEY
import com.mits.moviesapp.databinding.FragmentDetailBinding
import com.mits.moviesapp.domain.model.MediaDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    lateinit var mediaDetail: MediaDetail
    lateinit var detailState: DetailState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        // get args
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel.findIfMediaItemExistsInDB(API_KEY, args.mediaId, args.mediaType)

        // observe state
        lifecycleScope.launch {
            viewModel.detailState.collect {
                it.detailItem?.mediaType = args.mediaType
                it.detailItem?.let { it1 ->
                    mediaDetail = it1
                    updateUi(it1)
                }
                detailState = it
                if (it.isInWatchList) {
                    it.view?.let { it1 ->
                        Snackbar.make(it1, R.string.added, Snackbar.LENGTH_SHORT).show()
                    }
                    binding.detailBtn.setImageResource(R.drawable.check)
                }else {
                    it.view?.let { it1 ->
                        Snackbar.make(it1, R.string.deleted, Snackbar.LENGTH_SHORT).show()
                    }
                        binding.detailBtn.setImageResource(R.drawable.add)

                    }
                binding.detailProgressBar.isVisible = it.isLoading
                binding.detailErrorMsg.text = it.error
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        binding.detailBtn.setOnClickListener { view ->
            if (detailState.isInWatchList) {
                binding.detailBtn.setImageResource(R.drawable.add)
                viewModel.deleteMediaItemFromDB(mediaDetail, view)
            } else {
                binding.detailBtn.setImageResource(R.drawable.check)
                viewModel.insertMediaItemInDB(mediaDetail, view)
            }
        }

        return binding.root
    }

    private fun updateUi(mediaDetail: MediaDetail) {
        Glide.with(this).load("${Constants.IMAGES_BASE_URL}${mediaDetail.backDropPath}")
            .placeholder(R.drawable.media_placeholder).into(binding.detailBackDropImg)
        Glide.with(this).load("${Constants.IMAGES_BASE_URL}${mediaDetail.posterPath}")
            .placeholder(R.drawable.media_placeholder).into(binding.detailPosterImg)
        binding.detailTitle.text = mediaDetail.title
        if (mediaDetail.genre.isNotEmpty()) binding.detailGenre.text = mediaDetail.genre
        binding.detailSummary.text = mediaDetail.summary
        binding.topAppBar.title = mediaDetail.title
    }
}