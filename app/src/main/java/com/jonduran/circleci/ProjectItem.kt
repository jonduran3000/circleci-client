package com.jonduran.circleci

import com.jonduran.circleci.common.android.LayoutResource
import com.jonduran.circleci.common.list.BaseViewHolder
import com.jonduran.circleci.common.list.Item
import com.jonduran.circleci.databinding.ListItemProjectBinding

data class ProjectItem(val name: String, val username: String) : Item<ListItemProjectBinding> {
    override val layoutId: LayoutResource = LayoutResource(R.layout.list_item_project)

    override val uniqueId: Long = hashCode().toLong()

    override fun bind(holder: BaseViewHolder<ListItemProjectBinding>, payloads: MutableList<Any>?) {
        holder.binding.image
        holder.binding.name.text = "$username/$name"
    }

    override fun unbind(holder: BaseViewHolder<ListItemProjectBinding>) {
    }
}