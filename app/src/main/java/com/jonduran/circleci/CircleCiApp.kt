package com.jonduran.circleci

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class CircleCiApp : Application(), HasAndroidInjector {
    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    lateinit var component: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = createComponent().apply { inject(this@CircleCiApp) }
    }

    override fun androidInjector(): AndroidInjector<Any> = injector
}

fun CircleCiApp.createComponent(): AppComponent {
    return DaggerAppComponent.factory().create(this)
}