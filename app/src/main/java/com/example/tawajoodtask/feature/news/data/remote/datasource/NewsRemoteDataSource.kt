package com.example.tawajoodtask.feature.news.data.remote.datasource

import com.example.tawajoodtask.core.network.ApiResult
import com.example.tawajoodtask.core.network.BaseApiService
import com.example.tawajoodtask.feature.news.data.remote.api.NewsApi
import com.example.tawajoodtask.feature.news.data.remote.dto.NewsResponseDto
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsApi: NewsApi
) : BaseApiService() {

    suspend fun getTopHeadlines(
        category: String
    ): ApiResult<NewsResponseDto> {

        return safeApiCall {
            newsApi.getTopHeadlines(category = category)
        }
    }

    suspend fun searchNews(
        query: String
    ): ApiResult<NewsResponseDto> {

        return safeApiCall {
            newsApi.searchNews(query = query)
        }
    }
}