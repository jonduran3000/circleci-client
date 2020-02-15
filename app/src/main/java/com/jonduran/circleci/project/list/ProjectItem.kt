package com.jonduran.circleci.project.list

import com.jonduran.circleci.R
import com.jonduran.circleci.common.android.LayoutResource
import com.jonduran.circleci.common.ui.list.BaseViewHolder
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.databinding.ListItemProjectBinding

data class ProjectItem(val name: String, val username: String) : Item {
    override val layoutId: LayoutResource = LayoutResource(R.layout.list_item_project)

    override val uniqueId: Long = hashCode().toLong()

    override fun bind(holder: BaseViewHolder, payloads: MutableList<Any>?) {
        with(ListItemProjectBinding.bind(holder.itemView)) {
            image // TODO: load image
            repoLabel.text = "$username/$name"
        }
    }

    override fun unbind(holder: BaseViewHolder) {
    }
}