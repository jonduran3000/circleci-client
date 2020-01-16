package com.jonduran.circleci

import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.key.KeyEntryModule
import com.jonduran.circleci.project.list.ProjectListFragment
import com.jonduran.circleci.project.list.ProjectListModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [KeyEntryModule::class])
    abstract fun keyEntryFragment(): KeyEntryFragment

    @ContributesAndroidInjector(modules = [ProjectListModule::class])
    abstract fun projectListFragment(): ProjectListFragment
}