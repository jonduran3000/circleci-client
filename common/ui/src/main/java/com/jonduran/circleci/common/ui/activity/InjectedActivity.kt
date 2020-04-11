package com.jonduran.circleci.common.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class InjectedActivity : BaseActivity() {
    @Inject lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        supportFragmentManager.fragmentFactory = fragmentFactory
        super.onCreate(savedInstanceState)
    }
}