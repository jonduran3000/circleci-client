package com.jonduran.circleci.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectEntity(
    val name: String,
    val username: String,
    val type: SourceControl,
    @PrimaryKey val url: String
)