package com.mits.moviesapp.presentation.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mits.moviesapp.R
import com.mits.moviesapp.common.Constants.IMAGES_BASE_URL
import com.mits.moviesapp.common.enums.MediaType
import com.mits.moviesapp.databinding.AdapterSearchLayoutBinding
import com.mits.moviesapp.domain.model.SearchItem

class SearchAdapter(private val mediaItemListener: MediaItemListener) :
    ListAdapter<SearchItem, SearchAdapter.ViewHolder>(SearchItemDiffCallback()) {

    interface MediaItemListener {
        fun onItemClicked(mediaId: Int, mediaType: MediaType)
    }

    inner class ViewHolder(binding: AdapterSearchLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val mediaImg: ImageView = binding.mediaImg
        private val mediaTitle: TextView = binding.mediaTitle
        private val mediaRatings: TextView = binding.mediaRatings
        private val mediaParent: ConstraintLayout = binding.mediaParent

        fun bind(item: SearchItem) {
            Glide.with(itemView.context).load("$IMAGES_BASE_URL${item.imagePath}")
                .placeholder(R.drawable.media_placeholder).into(mediaImg)
            mediaTitle.text = item.title
            mediaRatings.text = item.ratings.toString()

            mediaParent.setOnClickListener {
                Log.e("item adapter id", item.id.toString())
                mediaItemListener.onItemClicked(item.id, item.mediaType) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

}

class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
        return oldItem == newItem
    }
}