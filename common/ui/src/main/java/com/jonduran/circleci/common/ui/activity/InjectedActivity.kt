package com.jonduran.circleci.common.ui.activity

import android.os.Bundle
import dagger.android.AndroidInjection

abstract class InjectedActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}