package com.jonduran.circleci.data

import com.jonduran.circleci.remote.annotation.Authorization
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataBindings {
    @Binds
    @Authorization
    abstract fun bindAuthorizationInterceptor(interceptor: AuthorizationInterceptor): Interceptor

    @Binds
    abstract fun bindDataStore(dataStore: AndroidDataStore): DataStore
}