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
    private lateinit var mediaDetail: MediaDetail

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

        // observe detail state
        lifecycleScope.launch {
            viewModel.detailState.collect { state ->
                state.detailItem?.let {
                    it.mediaType = args.mediaType
                    mediaDetail = it
                    updateUi(state)
                }
                updateBtn(state)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            NavHostFragment.findNavController(this).navigateUp()
        }

        binding.detailBtn.setOnClickListener { view ->
            if (viewModel.detailState.value.isInWatchList) {
                viewModel.deleteMediaItemFromDB(mediaDetail, view)
            } else {
                viewModel.insertMediaItemInDB(mediaDetail, view)
            }
        }

        return binding.root
    }


    private fun updateBtn(detailState: DetailState) {
        if (detailState.isInWatchList) {
            detailState.view?.let { view ->
                Snackbar.make(
                    view,
                    R.string.added,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            binding.detailBtn.setImageResource(R.drawable.check)
        } else {
            detailState.view?.let { view ->
                Snackbar.make(
                    view,
                    R.string.deleted,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            binding.detailBtn.setImageResource(R.drawable.add)
        }
    }

    private fun updateUi(detailState: DetailState) {
        Glide.with(this).load("${Constants.IMAGES_BASE_URL}${detailState.detailItem?.backDropPath}")
            .placeholder(R.drawable.media_placeholder).into(binding.detailBackDropImg)
        Glide.with(this).load("${Constants.IMAGES_BASE_URL}${detailState.detailItem?.posterPath}")
            .placeholder(R.drawable.media_placeholder).into(binding.detailPosterImg)
        binding.detailTitle.text = detailState.detailItem?.title
        if (!detailState.detailItem?.genre.isNullOrEmpty()) binding.detailGenre.text =
            detailState.detailItem?.genre
        binding.detailSummary.text = detailState.detailItem?.summary
        binding.topAppBar.title = detailState.detailItem?.title
        binding.detailProgressBar.isVisible = detailState.isLoading
        binding.detailErrorMsg.text = detailState.error
    }

}