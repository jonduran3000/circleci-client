package com.jonduran.circleci

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {
    @Binds
    abstract fun bindSavedStateOwner(owner: MainActivity): SavedStateRegistryOwner
}