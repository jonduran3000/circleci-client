package com.jonduran.circleci

import com.jonduran.circleci.common.android.LayoutResource
import com.jonduran.circleci.common.ui.list.BaseViewHolder
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.databinding.ListItemBackdropBinding

data class BackdropItem(val name: String) : Item<ListItemBackdropBinding> {
    override val layoutId: LayoutResource = LayoutResource(R.layout.list_item_backdrop)

    override val uniqueId: Long = name.hashCode().toLong()

    override fun bind(
        holder: BaseViewHolder<ListItemBackdropBinding>,
        payloads: MutableList<Any>?
    ) {
        holder.binding.root.text = name
    }

    override fun unbind(holder: BaseViewHolder<ListItemBackdropBinding>) {

    }
}