package com.jonduran.circleci.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import com.dropbox.android.external.store4.MemoryPolicy
import com.jonduran.circleci.cache.CacheModule
import com.jonduran.circleci.data.utils.checkBackgroundThread
import com.jonduran.circleci.remote.RemoteModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit

@Module(includes = [CacheModule::class, DataBindings::class, RemoteModule::class])
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences {
        checkBackgroundThread()
        val alias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "stable-preferences",
            alias,
            context,
            AES256_SIV,
            AES256_GCM
        )
    }

    @Provides
    fun provideMemoryPolicy(): MemoryPolicy {
        return MemoryPolicy.builder()
            .setExpireAfterAccess(5)
            .setExpireAfterTimeUnit(TimeUnit.MINUTES)
            .build()
    }
}