package com.example.moviedbapplication.common.extensions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviedbapplication.common.base.BasePagingSource

fun <Value : Any> setPager(
    pageSize: Int = BasePagingSource.LIMIT,
    initialLoadSize: Int = BasePagingSource.LIMIT,
    enablePlaceholders: Boolean = false,
    pagingSourceFactory: () -> BasePagingSource<Value>,
): Pager<Int, Value> {
    return Pager(
        config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = initialLoadSize,
            prefetchDistance = pageSize,
            enablePlaceholders = enablePlaceholders,
        ),
        pagingSourceFactory = pagingSourceFactory
    )
}