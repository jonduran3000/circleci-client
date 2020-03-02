package com.jonduran.circleci.common.ui.fragment

import androidx.annotation.LayoutRes
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class InjectingFragment(
    @LayoutRes layoutId: Int
) : InjectedFragment(layoutId), HasAndroidInjector {
    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}