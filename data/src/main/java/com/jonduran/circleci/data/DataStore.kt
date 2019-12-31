package com.jonduran.circleci.data

interface DataStore {
    fun setCredentials(credentials: String): Boolean
    fun getCredentials(): String?
    fun hasCredentials(): Boolean
}