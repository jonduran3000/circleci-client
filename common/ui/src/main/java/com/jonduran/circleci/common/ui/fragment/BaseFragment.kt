package com.jonduran.circleci.common.ui.fragment

import android.content.Context
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    protected val onBackPressed: (() -> Unit)? = null
    protected var onBackPressedEnabled: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (onBackPressed != null) {
            val callback = object : OnBackPressedCallback(onBackPressedEnabled) {
                override fun handleOnBackPressed() {
                    onBackPressed.invoke()
                }
            }
            (context as FragmentActivity).onBackPressedDispatcher.addCallback(this, callback)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}