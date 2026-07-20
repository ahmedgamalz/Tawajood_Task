package com.example.tawajoodtask.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tawajoodtask.feature.news.data.local.dao.ArticleDao
import com.example.tawajoodtask.feature.news.data.local.entity.ArticleEntity

@Database(
    entities = [
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

}