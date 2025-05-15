package com.example.moviedbapplication.ui.fragment.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviedbapplication.common.base.BaseViewModel
import com.example.moviedbapplication.common.base.Effect
import com.example.moviedbapplication.common.base.Event
import com.example.moviedbapplication.common.base.State
import com.example.moviedbapplication.common.util.FailViewType
import com.example.moviedbapplication.domain.model.home.movienowplaying.MovieNowPlayingUI
import com.example.moviedbapplication.domain.model.home.movietoprated.MovieTopRatedUI
import com.example.moviedbapplication.domain.usecase.home.movienowplaying.MovieNowPlayingUseCase
import com.example.moviedbapplication.domain.usecase.home.movietoprated.MovieTopRatedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieNowPlayingUseCase: MovieNowPlayingUseCase,
    private val movieTopRatedUseCase: MovieTopRatedUseCase
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    init {
        getNowPlaying()
        getTopRated()
    }

    override fun setInitialState() = HomeState()

    override fun handleEvents(event: HomeEvent) {
        when (event) {
            is HomeEvent.MovieClicked -> {
                setEffect { HomeEffect.NavigateToDetail(event.id) }
            }
        }
    }

    private fun getNowPlaying() {
        viewModelScope.launch {
            movieNowPlayingUseCase().cachedIn(viewModelScope).collect { resource ->
                setState { copy(nowPlaying = resource) }
            }
        }
    }

    private fun getTopRated() {
        viewModelScope.launch {
            movieTopRatedUseCase().cachedIn(viewModelScope).collect { resource ->
                setState { copy(topRated = resource) }
            }
        }
    }

}

sealed interface HomeEvent : Event {
    data class MovieClicked(val id: String) : HomeEvent
}

data class HomeState(
    val isLoading: Boolean = false,
    val nowPlaying: PagingData<MovieNowPlayingUI>? = null,
    val topRated: PagingData<MovieTopRatedUI>? = null
) : State

sealed interface HomeEffect : Effect {
    data class NavigateToDetail(val id: String) : HomeEffect
    data class ShowFail(val message: Int, val failViewType: FailViewType) : HomeEffect
}