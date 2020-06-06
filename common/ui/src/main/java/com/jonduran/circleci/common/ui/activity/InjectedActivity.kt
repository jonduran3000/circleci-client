package com.jonduran.circleci.common.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class InjectedActivity : BaseActivity() {
    @Inject override lateinit var fragmentFactory: FragmentFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}