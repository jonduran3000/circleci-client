package com.jonduran.circleci.data

import kotlinx.coroutines.flow.Flow

interface DataSource<K, V> {
    fun stream(key: K): Flow<V>
    suspend fun get(key: K): V
    suspend fun fresh(key: K): V
    suspend fun clear(key: K)
}