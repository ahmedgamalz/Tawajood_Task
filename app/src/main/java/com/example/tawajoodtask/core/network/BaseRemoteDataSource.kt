package com.example.tawajoodtask.core.network

import retrofit2.Response

abstract class BaseRemoteDataSource {

    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> {

        return try {

            val response = apiCall()

            if (response.isSuccessful) {

                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error("Empty Body")

            } else {

                ApiResult.Error(
                    message = response.message(),
                    code = response.code()
                )

            }

        } catch (e: Exception) {

            ApiResult.Error(e.localizedMessage ?: "Unknown Error")

        }
    }
}