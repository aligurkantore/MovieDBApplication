package com.example.moviedbapplication.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedbapplication.common.base.BaseFragment
import com.example.moviedbapplication.common.extensions.collectIn
import com.example.moviedbapplication.common.extensions.navigateSafe
import com.example.moviedbapplication.databinding.FragmentHomeBinding
import com.example.moviedbapplication.ui.fragment.home.homeadapter.MovieNowPlayingAdapter
import com.example.moviedbapplication.ui.fragment.home.homeadapter.MovieTopRatedAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeEvent, HomeState, HomeEffect>() {

    override val viewModel: HomeViewModel by viewModels()

    private val nowPlayingAdapter = MovieNowPlayingAdapter { id ->
        viewModel.setEvent(HomeEvent.MovieClicked(id))
    }

    private val topRatedAdapter = MovieTopRatedAdapter { id ->
        viewModel.setEvent(HomeEvent.MovieClicked(id))
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun bindScreen() {
        with(binding) {
            recyclerNowPlaying.adapter = nowPlayingAdapter
            recyclerNowPlaying.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )

            recyclerTopRated.adapter = topRatedAdapter
            recyclerTopRated.layoutManager = GridLayoutManager(context, 2)
        }

        collectState()
        collectEffect()
    }

    private fun collectState() = with(binding) {
        viewModel.state.collectIn(viewLifecycleOwner) { state ->
            state.nowPlaying?.let { nowPlaying ->
                lifecycleScope.launch {
                    nowPlayingAdapter.submitData(nowPlaying)
                }

            }

            state.topRated?.let { topRated ->
                lifecycleScope.launch {
                    topRatedAdapter.submitData(topRated)
                }
            }
        }
    }

    private fun collectEffect() {
        viewModel.effect.collectIn(viewLifecycleOwner) { effect ->
            when (effect) {
                is HomeEffect.NavigateToDetail -> {
                    findNavController().navigateSafe(
                        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                            effect.id
                        )
                    )
                }

                is HomeEffect.ShowFail -> {
                    Toast.makeText(requireContext(), getString(effect.message), Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }
    }

    override fun onDestroyView() {
        binding.apply {
            recyclerNowPlaying.adapter = null
            recyclerTopRated.adapter = null
        }
        super.onDestroyView()
    }


}