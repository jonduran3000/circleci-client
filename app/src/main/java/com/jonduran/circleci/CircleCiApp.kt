package com.jonduran.circleci

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class CircleCiApp : Application(), HasAndroidInjector {
    @Inject lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Injector.inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = injector
}