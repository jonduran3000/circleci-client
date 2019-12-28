package com.jonduran.circleci.common.ui.list

import androidx.viewbinding.ViewBinding
import com.jonduran.circleci.common.android.LayoutResource

interface Item<V : ViewBinding> {
    val layoutId: LayoutResource
    val uniqueId: Long
    val spanSize: Int get() = 1
    fun bind(holder: BaseViewHolder<V>, payloads: MutableList<Any>? = null)
    fun unbind(holder: BaseViewHolder<V>)
}