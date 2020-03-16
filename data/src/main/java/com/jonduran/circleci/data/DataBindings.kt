package com.jonduran.circleci.data

import com.jonduran.circleci.remote.annotation.Authorization
import dagger.Binds
import dagger.Module
import okhttp3.Interceptor

@Module
abstract class DataBindings {
    @Binds
    @Authorization
    abstract fun bindAuthorizationInterceptor(interceptor: AuthorizationInterceptor): Interceptor

    @Binds
    abstract fun bindDataStore(dataStore: AndroidDataStore): DataStore
}