package com.example.tawajoodtask.core.network

import retrofit2.Response
import java.io.IOException

abstract class BaseApiService {

    protected suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): ApiResult<T> {

        return try {

            val response = apiCall()

            if (response.isSuccessful) {

                response.body()?.let {
                    ApiResult.Success(it)
                } ?: ApiResult.Error(
                    message = "Response body is null",
                    code = response.code()
                )

            } else {

                ApiResult.Error(
                    message = response.message(),
                    code = response.code()
                )

            }

        } catch (e: IOException) {

            ApiResult.Error("No Internet Connection")

        } catch (e: Exception) {

            ApiResult.Error(e.message ?: "Unknown Error")

        }
    }
}