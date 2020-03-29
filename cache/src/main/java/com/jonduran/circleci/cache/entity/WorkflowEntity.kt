package com.jonduran.circleci.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class WorkflowEntity(
    @PrimaryKey val id: UUID,
    val name: String,
    val totalBuildTimeInMillis: Long
)