package com.example.moviedbapplication.ui.fragment.detail

import androidx.lifecycle.viewModelScope
import com.example.moviedbapplication.common.base.BaseViewModel
import com.example.moviedbapplication.common.base.Effect
import com.example.moviedbapplication.common.base.Event
import com.example.moviedbapplication.common.base.State
import com.example.moviedbapplication.common.util.FailViewType
import com.example.moviedbapplication.common.util.Resource
import com.example.moviedbapplication.domain.model.home.detail.MovieDetailUI
import com.example.moviedbapplication.domain.usecase.home.detail.MovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : BaseViewModel<MovieDetailEvent, MovieDetailState, MovieDetailEffect>() {


    override fun setInitialState() = MovieDetailState(false)


    override fun handleEvents(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.OnBackClick -> {
                setEffect { MovieDetailEffect.GoToBack }
            }
        }
    }

    fun getDetail(movieId: String) = viewModelScope.launch {
        movieDetailUseCase.invoke(movieId)
            .onStart { setState { copy(isLoading = true) } }
            .onCompletion { setState { copy(isLoading = false) } }
            .collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        val detail = result.data
                        setState { copy(isLoading = false, movieDetail = detail) }
                    }

                    is Resource.Fail -> {
                        setEffect {
                            MovieDetailEffect.ShowFail(
                                result.message,
                                result.failViewType
                            )
                        }
                    }
                }
            }
    }
}

sealed interface MovieDetailEvent : Event {
    data object OnBackClick : MovieDetailEvent
}

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetailUI? = null
) : State

sealed interface MovieDetailEffect : Effect {
    data object GoToBack : MovieDetailEffect
    data class ShowFail(val message: Int, val failViewType: FailViewType) : MovieDetailEffect

}