package com.jonduran.circleci.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jonduran.circleci.cache.entity.BuildEntity
import java.util.UUID

@Dao
interface BuildDao {
    @Query("""
        SELECT * FROM BuildEntity ORDER BY queuedAt DESC;
    """)
    suspend fun getBuilds(): List<BuildEntity>

    @Query("""
        SELECT * FROM BuildEntity WHERE workflowId = :id ORDER BY queuedAt DESC;
    """)
    suspend fun getWorkflow(id: UUID): List<BuildEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(build: BuildEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(builds: List<BuildEntity>)
}