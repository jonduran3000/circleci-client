package com.jonduran.circleci.common.ui.activity

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class InjectingActivity : InjectedActivity(), HasAndroidInjector {
    @Inject lateinit var injector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = injector
}