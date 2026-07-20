package com.example.tawajoodtask.feature.news.data.remote.api
import com.example.tawajoodtask.feature.news.data.remote.dto.NewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String
    ): Response<NewsResponseDto>

    @GET("everything")
    suspend fun searchNews(
        @Query("q") query: String
    ): Response<NewsResponseDto>
}