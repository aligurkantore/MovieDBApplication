package com.example.moviedbapplication.ui.fragment.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.moviedbapplication.common.base.BaseFragment
import com.example.moviedbapplication.common.extensions.collectIn
import com.example.moviedbapplication.common.extensions.loadImage
import com.example.moviedbapplication.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment :
    BaseFragment<FragmentMovieDetailBinding, MovieDetailEvent, MovieDetailState, MovieDetailEffect>() {

    override val viewModel: MovieDetailViewModel by viewModels()

    private val args by navArgs<MovieDetailFragmentArgs>()

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding {
        return FragmentMovieDetailBinding.inflate(layoutInflater)
    }

    override fun bindScreen() {
        viewModel.getDetail(args.movieId)
        collectState()
    }

    private fun collectState() {
        viewModel.state.collectIn(viewLifecycleOwner) { state ->
            // Yükleniyor ise göster
            //   binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

            state.movieDetail?.let { detail ->
                binding.apply {
                    imagePoster.loadImage(detail.posterPath)
                    textTitle.text = detail.title
                    textOverview.text = detail.overview
                }
            }
        }
    }

}