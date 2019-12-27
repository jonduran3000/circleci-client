package com.jonduran.circleci.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProjectDao {
    @Query(value = """
        SELECT * FROM ProjectEntity 
        WHERE username LIKE :username 
        AND type IN(:types) 
        ORDER BY name COLLATE NOCASE ASC;
    """)
    suspend fun getProjects(username: String, types: Array<SourceControl>): List<ProjectEntity>

    @Query(value = "SELECT DISTINCT (username) FROM ProjectEntity")
    suspend fun getProjectOwnerNames(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: ProjectEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(projects: List<ProjectEntity>)
}