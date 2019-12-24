package com.jonduran.circleci.cache

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object CacheModule {
    @Provides
    @Singleton
    fun providesDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "stable-db")
            .build()
    }
}