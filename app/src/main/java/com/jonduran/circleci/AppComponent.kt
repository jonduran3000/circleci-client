package com.jonduran.circleci

import android.app.Application
import com.jonduran.circleci.data.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBindings::class,
        AssistedBindings::class,
        FragmentBindings::class,
        DataModule::class,
        ViewModelBindings::class
    ]
)
interface AppComponent {
    fun inject(app: CircleCiApp)

    @Component.Factory
    interface Builder {
        fun create(@BindsInstance application: Application): AppComponent
    }
}