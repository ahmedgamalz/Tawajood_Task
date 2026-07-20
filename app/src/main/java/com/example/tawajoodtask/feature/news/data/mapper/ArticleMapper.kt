package com.example.tawajoodtask.feature.news.data.mapper

import com.example.tawajoodtask.feature.news.data.local.entity.ArticleEntity
import com.example.tawajoodtask.feature.news.data.remote.dto.ArticleDto
import com.example.tawajoodtask.feature.news.domain.model.Article

fun ArticleDto.toEntity(
    category: String
): ArticleEntity {

    return ArticleEntity(
        url = url,
        category = category,
        sourceName = source.name,
        author = author,
        title = title,
        description = description,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        content = content
    )
}

fun ArticleEntity.toDomain(): Article {

    return Article(
        url = url,
        category = category,
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content
    )
}

fun Article.toEntity(): ArticleEntity {

    return ArticleEntity(
        url = url,
        category = category,
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        imageUrl = imageUrl,
        publishedAt = publishedAt,
        content = content
    )
}