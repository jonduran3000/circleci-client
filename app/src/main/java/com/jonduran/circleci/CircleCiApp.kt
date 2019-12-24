package com.jonduran.circleci

import android.app.Application

class CircleCiApp : Application() {
    companion object {
        internal lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = inject()
    }
}

fun CircleCiApp.inject(): AppComponent {
    return DaggerAppComponent.factory()
        .create(this)
        .apply {
            inject(this@inject)
        }
}