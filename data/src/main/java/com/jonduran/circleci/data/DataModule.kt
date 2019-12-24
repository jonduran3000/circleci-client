package com.jonduran.circleci.data

import com.jonduran.circleci.cache.CacheModule
import com.jonduran.circleci.remote.RemoteModule
import dagger.Module

@Module(includes = [CacheModule::class, RemoteModule::class])
object DataModule