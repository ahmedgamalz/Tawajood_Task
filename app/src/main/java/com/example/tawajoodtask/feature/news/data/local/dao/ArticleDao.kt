package com.example.tawajoodtask.feature.news.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tawajoodtask.core.database.BaseDao
import com.example.tawajoodtask.feature.news.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao : BaseDao<ArticleEntity> {

    @Query("SELECT * FROM articles WHERE category = :category")
    fun getArticles(category: String): Flow<List<ArticleEntity>>

    @Query("DELETE FROM articles WHERE category = :category")
    suspend fun deleteByCategory(category: String)

    @Transaction
    suspend fun replaceArticles(
        category: String,
        articles: List<ArticleEntity>
    ) {
        deleteByCategory(category)
        insertAll(articles)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE url = :url LIMIT 1")
    suspend fun getArticleByUrl(url: String): ArticleEntity?


}