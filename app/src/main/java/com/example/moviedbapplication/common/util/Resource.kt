package com.example.moviedbapplication.common.util

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Fail(val message: Int, val failViewType: FailViewType = FailViewType.Popup) : Resource<Nothing>()
}

suspend fun <T : Any, N : Any> Resource<T>.onSuccess(data: suspend (T) -> N): Resource<N> {
    return when (this) {
        is Resource.Success -> {
            try {
                Resource.Success(data(this.data))
            } catch (exception: Exception) {
                Resource.Fail(message = 0, failViewType = FailViewType.Popup)
            }
        }
        is Resource.Fail -> this
    }
}

suspend fun <T : Any, N : Any> Resource<T>.getData(dataCallBack: suspend (T) -> N?): N? {
    return when (this) {
        is Resource.Success -> {
            try {
                dataCallBack(this.data)
            } catch (exception: Exception) {
                null
            }
        }

        is Resource.Fail -> null
    }
}

enum class FailViewType {
    Popup,
    FullPopup,
    None
}