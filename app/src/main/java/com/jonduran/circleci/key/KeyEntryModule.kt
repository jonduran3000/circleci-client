package com.jonduran.circleci.key

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class KeyEntryModule {
    @Binds
    @Named("KeyEntry")
    abstract fun bindSavedStateOwner(owner: KeyEntryFragment): SavedStateRegistryOwner
}