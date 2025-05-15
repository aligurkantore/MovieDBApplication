package com.example.moviedbapplication.ui.fragment.home.homeadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.common.extensions.loadImage
import com.example.moviedbapplication.databinding.ItemNowPlayingBinding
import com.example.moviedbapplication.domain.model.home.movienowplaying.MovieNowPlayingUI

class MovieNowPlayingAdapter (
    private val onItemClick: (String) -> Unit
) : PagingDataAdapter<MovieNowPlayingUI, MovieNowPlayingAdapter.MovieNowPlayingViewHolder>(MovieNowPlayingComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieNowPlayingViewHolder {
        val binding = ItemNowPlayingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieNowPlayingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieNowPlayingViewHolder, position: Int) {
        val nowPlaying = getItem(position)
        nowPlaying?.let {
            holder.bind(it)
        }
    }

    inner class MovieNowPlayingViewHolder(private val binding: ItemNowPlayingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nowPlaying: MovieNowPlayingUI) {
            binding.apply {
                textViewTitle.text = nowPlaying.title
                imageViewMovie.loadImage(nowPlaying.posterPath)

                root.setOnClickListener {
                    onItemClick(nowPlaying.id)
                }
            }

        }
    }

    companion object {
        val MovieNowPlayingComparator = object : DiffUtil.ItemCallback<MovieNowPlayingUI>() {
            override fun areItemsTheSame(oldItem: MovieNowPlayingUI, newItem: MovieNowPlayingUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieNowPlayingUI, newItem: MovieNowPlayingUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}