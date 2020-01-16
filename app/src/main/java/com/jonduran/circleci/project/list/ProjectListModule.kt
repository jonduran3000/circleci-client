package com.jonduran.circleci.project.list

import androidx.savedstate.SavedStateRegistryOwner
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class ProjectListModule {
    @Binds
    @Named("ProjectList")
    abstract fun bindSavedStateOwner(owner: ProjectListFragment): SavedStateRegistryOwner
}