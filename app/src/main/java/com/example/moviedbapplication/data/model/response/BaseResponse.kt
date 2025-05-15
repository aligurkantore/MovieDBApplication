package com.example.moviedbapplication.data.model.response

open class BaseResponse(
    val statusCode: Int? = null,
    val timestamp: String? = null,
    val path: String? = null,
    val message: ErrorMessage? = null,
    val requestId: String? = null,
)

data class ErrorMessage(
    val message: String? = null,
    val error: String? = null,
    val statusCode: Int? = null
)