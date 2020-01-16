package com.jonduran.circleci.common.ui.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import dagger.android.AndroidInjection

abstract class InjectedActivity<V : ViewBinding> : BaseActivity<V>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
}