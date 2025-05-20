package com.example.moviedbapplication.ui.fragment.home.homeadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.common.extensions.loadImage
import com.example.moviedbapplication.databinding.ItemTopRatedBinding
import com.example.moviedbapplication.domain.model.home.movienowplaying.MovieNowPlayingUI

class MoviePopularAdapter(
    private val onItemClick: (String) -> Unit
) : PagingDataAdapter<MovieNowPlayingUI, MoviePopularAdapter.MoviePopularViewHolder>(
    MoviePopularComparator
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePopularViewHolder {
        val binding =
            ItemTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviePopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviePopularViewHolder, position: Int) {
        val nowPlaying = getItem(position)
        nowPlaying?.let {
            holder.bind(it)
        }
    }

    inner class MoviePopularViewHolder(private val binding: ItemTopRatedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popular: MovieNowPlayingUI) {
            binding.apply {
                textViewTitle.text = popular.title
                imageViewMovie.loadImage(popular.posterPath)

                root.setOnClickListener {
                    onItemClick(popular.id)
                }
            }

        }
    }

    companion object {
        val MoviePopularComparator = object : DiffUtil.ItemCallback<MovieNowPlayingUI>() {
            override fun areItemsTheSame(
                oldItem: MovieNowPlayingUI,
                newItem: MovieNowPlayingUI
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieNowPlayingUI,
                newItem: MovieNowPlayingUI
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}