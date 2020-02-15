package com.jonduran.circleci.common.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.jonduran.circleci.common.ui.utils.autoCleared

typealias Binder<B> = (LayoutInflater, ViewGroup?, Boolean) -> B

abstract class BaseFragment<B : ViewBinding> : Fragment() {
    protected var binding by autoCleared<B>() // https://stackoverflow.com/a/59504797
    abstract val inflateBinding: Binder<B>

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
            requireActivity().onBackPressedDispatcher.addCallback(this, callback)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateBinding(inflater, container, false)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}