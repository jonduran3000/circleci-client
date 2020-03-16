package com.jonduran.circleci

import com.jonduran.circleci.build.BuildListFragment
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindings {
    @ContributesAndroidInjector
    abstract fun keyEntryFragment(): KeyEntryFragment

    @ContributesAndroidInjector
    abstract fun projectListFragment(): ProjectListFragment

    @ContributesAndroidInjector
    abstract fun buildListFragment(): BuildListFragment
}