package com.jonduran.circleci.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonduran.circleci.cache.dao.BuildDao
import com.jonduran.circleci.cache.dao.ProjectDao
import com.jonduran.circleci.cache.entity.BuildEntity
import com.jonduran.circleci.cache.entity.ProjectEntity
import com.jonduran.circleci.cache.entity.WorkflowEntity

@Database(
    entities = [
        BuildEntity::class,
        ProjectEntity::class,
        WorkflowEntity::class
    ], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun buildDao(): BuildDao
    abstract fun projectDao(): ProjectDao
}