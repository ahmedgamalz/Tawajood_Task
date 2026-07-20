package com.example.tawajoodtask.feature.news.data.repository

import com.example.tawajoodtask.core.network.ApiResult
import com.example.tawajoodtask.core.network.NetworkMonitor
import com.example.tawajoodtask.feature.news.data.local.dao.ArticleDao
import com.example.tawajoodtask.feature.news.data.mapper.toDomain
import com.example.tawajoodtask.feature.news.data.mapper.toEntity
import com.example.tawajoodtask.feature.news.data.remote.datasource.NewsRemoteDataSource
import com.example.tawajoodtask.feature.news.domain.model.Article
import com.example.tawajoodtask.feature.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val articleDao: ArticleDao,
    private val networkMonitor: NetworkMonitor
) : NewsRepository {

    override fun getNews(category: String): Flow<List<Article>> {

        return articleDao.getArticles(category).map { articles ->

            if (networkMonitor.isConnected()) {
                articles.map { it.toDomain() }
            } else {
                articles.shuffled().map { it.toDomain() }
            }
        }
    }

    override suspend fun refreshNews(category: String) {

        if (!networkMonitor.isConnected()) return

        when (val result = remoteDataSource.getTopHeadlines(category)) {

            is ApiResult.Success -> {

                articleDao.deleteByCategory(category)

                articleDao.insertArticles(
                    result.data.articles.map {
                        it.toEntity(category)
                    }
                )
            }

            is ApiResult.Error -> {
            }

            ApiResult.Loading -> Unit
        }
    }

    override suspend fun searchNews(query: String): List<Article> {

        return when (val result = remoteDataSource.searchNews(query)) {

            is ApiResult.Success -> {
                result.data.articles.map {
                    it.toEntity("search").toDomain()
                }
            }

            is ApiResult.Error -> emptyList()

            ApiResult.Loading -> emptyList()
        }
    }

    override suspend fun getArticleByUrl(
        url: String
    ): Article? {

        return articleDao
            .getArticleByUrl(url)
            ?.toDomain()
    }
}