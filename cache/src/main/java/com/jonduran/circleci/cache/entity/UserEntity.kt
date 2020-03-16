package com.jonduran.circleci.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val login: String,
    val name: String,
    val avatarUrl: String
)