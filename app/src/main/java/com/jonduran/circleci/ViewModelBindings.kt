package com.jonduran.circleci

import androidx.lifecycle.ViewModel
import com.jonduran.circleci.build.BuildListViewModel
import com.jonduran.circleci.key.KeyEntryViewModel
import com.jonduran.circleci.project.list.ProjectListViewModel
import com.jonduran.circleci.viewmodel.AssistedProvider
import com.jonduran.circleci.viewmodel.InjectedViewModelFactory
import com.jonduran.circleci.viewmodel.InjectedViewModelFactory_AssistedFactory
import com.jonduran.circleci.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelBindings {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMain(provider: MainViewModel.Provider): AssistedProvider<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(BuildListViewModel::class)
    abstract fun bindBuildList(
        provider: BuildListViewModel.Provider
    ): AssistedProvider<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(KeyEntryViewModel::class)
    abstract fun bindKeyEntry(
        provider: KeyEntryViewModel.Provider
    ): AssistedProvider<out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(ProjectListViewModel::class)
    abstract fun bindProjectList(
        provider: ProjectListViewModel.Provider
    ): AssistedProvider<out ViewModel>

    @Binds
    abstract fun bindFactory(
        factory: InjectedViewModelFactory_AssistedFactory
    ): InjectedViewModelFactory.Producer
}