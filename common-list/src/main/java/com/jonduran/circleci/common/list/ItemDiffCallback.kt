package com.jonduran.circleci.common.list

import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import java.util.Objects

class ItemDiffCallback<V : ViewBinding, I : Item<V>> : DiffUtil.ItemCallback<I>() {
    override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
        return oldItem.uniqueId == newItem.uniqueId
    }

    override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
        return Objects.equals(oldItem, newItem)
    }
}