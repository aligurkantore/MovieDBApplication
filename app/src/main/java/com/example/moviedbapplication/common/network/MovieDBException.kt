package com.example.moviedbapplication.common.network

import com.example.moviedbapplication.common.util.FailViewType
import java.io.IOException

class NetworkUnavailableException : IOException()

class PagingFail(override val message: String, val failViewType: FailViewType) : Exception(message)