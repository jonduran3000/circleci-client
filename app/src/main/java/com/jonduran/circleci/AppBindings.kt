package com.jonduran.circleci

import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppBindings {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun keyEntryFragment(): KeyEntryFragment

    @ContributesAndroidInjector
    abstract fun projectListFragment(): ProjectListFragment
}