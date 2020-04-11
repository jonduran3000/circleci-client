package com.jonduran.circleci

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.jonduran.circleci.build.BuildListFragment
import com.jonduran.circleci.common.ui.fragment.FragmentKey
import com.jonduran.circleci.common.ui.fragment.InjectedFragmentFactory
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentBindings {
    @Binds
    @IntoMap
    @FragmentKey(KeyEntryFragment::class)
    abstract fun bindKeyEntry(fragment: KeyEntryFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(ProjectListFragment::class)
    abstract fun bindProjectList(fragment: ProjectListFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BuildListFragment::class)
    abstract fun bindBuildList(fragment: BuildListFragment): Fragment

    @Binds
    abstract fun bindFactory(factory: InjectedFragmentFactory): FragmentFactory
}