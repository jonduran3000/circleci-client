package com.jonduran.circleci.data

import android.content.SharedPreferences
import javax.inject.Inject

class AndroidDataStore @Inject constructor(private val preferences: SharedPreferences) : DataStore {
    override fun setCredentials(credentials: String): Boolean {
        return preferences.edit()
            .putString("credentials", credentials)
            .commit()
    }

    override fun getCredentials(): String? {
        return preferences.getString("credentials", null)
    }

    override fun hasCredentials(): Boolean {
        return preferences.contains("credentials")
    }
}