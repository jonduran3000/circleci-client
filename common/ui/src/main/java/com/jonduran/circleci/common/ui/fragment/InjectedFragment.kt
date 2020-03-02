package com.jonduran.circleci.common.ui.fragment

import android.content.Context
import androidx.annotation.LayoutRes
import dagger.android.support.AndroidSupportInjection

abstract class InjectedFragment(@LayoutRes layoutId: Int) : BaseFragment(layoutId) {
    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}