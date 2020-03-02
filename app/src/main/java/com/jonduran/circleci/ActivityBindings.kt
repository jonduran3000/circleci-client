package com.jonduran.circleci

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindings {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity
}