package com.example.tawajoodtask.feature.news.domain.model

data class Article(

    val url: String,

    val category: String,

    val sourceName: String,

    val author: String?,

    val title: String,

    val description: String?,

    val imageUrl: String?,

    val publishedAt: String,

    val content: String?

)