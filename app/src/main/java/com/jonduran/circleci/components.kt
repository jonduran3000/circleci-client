package com.jonduran.circleci

import android.app.Application
import com.jonduran.circleci.annotations.ActivityScope
import com.jonduran.circleci.annotations.FragmentScope
import com.jonduran.circleci.data.DataModule
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {
    fun inject(app: CircleCiApp)
    fun mainComponent(): MainComponent.Builder

    @Component.Factory
    interface Builder {
        fun create(@BindsInstance application: Application): AppComponent
    }
}

@ActivityScope
@Subcomponent
interface MainComponent {
    fun inject(activity: MainActivity)
    fun keyEntryComponent(): KeyEntryComponent.Builder
    fun buildListComponent(): BuildListComponent.Builder

    @Subcomponent.Builder
    interface Builder {
        fun create(): MainComponent
    }
}

@FragmentScope
@Subcomponent
interface KeyEntryComponent {
    fun inject(fragment: KeyEntryFragment)

    @Subcomponent.Builder
    interface Builder {
        fun create(): KeyEntryComponent
    }
}

@FragmentScope
@Subcomponent
interface BuildListComponent {
    fun inject(fragment: ProjectListFragment)

    @Subcomponent.Builder
    interface Builder {
        fun create(): BuildListComponent
    }
}