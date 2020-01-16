package com.jonduran.circleci.common.ui.fragment

import android.content.Context
import androidx.viewbinding.ViewBinding
import dagger.android.support.AndroidSupportInjection

abstract class InjectedFragment<V : ViewBinding> : BaseFragment<V>() {
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}