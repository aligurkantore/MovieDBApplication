package com.example.moviedbapplication.ui.activity.main

import com.example.moviedbapplication.common.base.BaseViewModel
import com.example.moviedbapplication.common.base.Effect
import com.example.moviedbapplication.common.base.Event
import com.example.moviedbapplication.common.base.State

class MainViewModel : BaseViewModel<MainEvent, MainState, MainEffect>() {

    override fun setInitialState() = MainState()

    override fun handleEvents(event: MainEvent) {
        //Event işleme mantığı
    }
}

sealed interface MainEvent : Event

data class MainState(
    val isLoading: Boolean = false
): State

sealed interface MainEffect : Effect