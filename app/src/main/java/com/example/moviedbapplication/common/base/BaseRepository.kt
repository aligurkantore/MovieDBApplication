package com.example.moviedbapplication.common.base

import android.util.Log
import androidx.paging.PagingData
import com.example.moviedbapplication.common.extensions.setPager
import com.example.moviedbapplication.common.network.PagingFail
import com.example.moviedbapplication.common.util.FailViewType
import com.example.moviedbapplication.common.util.Resource
import com.example.moviedbapplication.data.model.response.BaseResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val errorResponse: BaseResponse? = convertErrorBody(response.errorBody())
                    Log.e("API_DEBUG", "API error: ${response.code()}, $errorResponse")
                    Resource.Fail(0, FailViewType.Popup)
                }
            } catch (e: HttpException) {
                Log.e("API_DEBUG", "HttpException", e)
                Resource.Fail(0, FailViewType.Popup)
            } catch (e: IOException) {
                Log.e("API_DEBUG", "IOException", e)
                Resource.Fail(0, FailViewType.Popup)
            } catch (e: Exception) {
                Log.e("API_DEBUG", "Unexpected error", e)
                Resource.Fail(0, FailViewType.Popup)
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): BaseResponse? {
        return try {
            val errorString = errorBody?.string()
            Log.d("API_DEBUG", "Error body: $errorString")
            Gson().fromJson(errorString, BaseResponse::class.java)
        } catch (exception: Exception) {
            Log.e("API_DEBUG", "Error parsing error body", exception)
            null
        }
    }

    suspend fun <T : Any> safeApiCallPaging(
        loadDataFromApi: suspend (page: Int, pageSize: Int) -> Resource<List<T>>
    ): Flow<PagingData<T>> {
        return setPager (
            pagingSourceFactory = {
                BasePagingSource { page, pageSize ->
                    Log.d("API_DEBUG", "Paging: Requesting page $page with size $pageSize")
                    when (val result = loadDataFromApi(page, pageSize)) {
                        is Resource.Success -> result.data
                        is Resource.Fail -> {
                            Log.e("API_DEBUG", "Paging failed: ${result.message}")
                            throw PagingFail(result.message.toString(), result.failViewType)
                        }
                    }
                }
            }
        ).flow
    }
}