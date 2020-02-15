package com.jonduran.circleci.common.ui.list

import com.jonduran.circleci.common.android.LayoutResource

interface Item {
    val layoutId: LayoutResource
    val uniqueId: Long
    val spanSize: Int get() = 1
    fun bind(holder: BaseViewHolder, payloads: MutableList<Any>? = null)
    fun unbind(holder: BaseViewHolder)
}