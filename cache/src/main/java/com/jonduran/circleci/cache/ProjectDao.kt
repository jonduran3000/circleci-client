package com.jonduran.circleci.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProjectDao {
    @Query("SELECT * FROM ProjectEntity WHERE username LIKE :username AND type IN(:types)")
    suspend fun getProjects(username: String, types: Array<SourceControl>): List<ProjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: ProjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(projects: List<ProjectEntity>)
}