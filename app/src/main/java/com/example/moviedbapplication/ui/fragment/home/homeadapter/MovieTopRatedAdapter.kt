package com.example.moviedbapplication.ui.fragment.home.homeadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedbapplication.common.extensions.loadImage
import com.example.moviedbapplication.databinding.ItemTopRatedBinding
import com.example.moviedbapplication.domain.model.home.movietoprated.MovieTopRatedUI

class MovieTopRatedAdapter (
    private val onItemClick: (String) -> Unit
) : PagingDataAdapter<MovieTopRatedUI, MovieTopRatedAdapter.MovieTopRatedViewHolder>(MovieTopRatedComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTopRatedViewHolder {
        val binding = ItemTopRatedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTopRatedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieTopRatedViewHolder, position: Int) {
        val topRated = getItem(position)
        topRated?.let {
            holder.bind(it)
        }
    }

    inner class MovieTopRatedViewHolder(private val binding: ItemTopRatedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(topRated: MovieTopRatedUI) {
            binding.apply {
                textViewTitle.text = topRated.name
                imageViewMovie.loadImage(topRated.posterUrl)

                root.setOnClickListener {
                    onItemClick(topRated.id)
                }
            }

        }
    }

    companion object {
        val MovieTopRatedComparator = object : DiffUtil.ItemCallback<MovieTopRatedUI>() {
            override fun areItemsTheSame(oldItem: MovieTopRatedUI, newItem: MovieTopRatedUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieTopRatedUI, newItem: MovieTopRatedUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}