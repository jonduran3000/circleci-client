package com.jonduran.circleci.data

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import com.jonduran.circleci.cache.CacheModule
import com.jonduran.circleci.remote.RemoteModule
import dagger.Module
import dagger.Provides

@Module(includes = [CacheModule::class, DataBindings::class, RemoteModule::class])
object DataModule {
    @Provides
    fun providePreferences(app: Application): SharedPreferences {
        checkBackgroundThread()
        val alias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "stable-preferences",
            alias,
            app,
            AES256_SIV,
            AES256_GCM
        )
    }
}