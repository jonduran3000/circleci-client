package com.jonduran.circleci.common.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<V : ViewBinding, I : Item<V>> : ListAdapter<I, BaseViewHolder<V>>(
    ItemDiffCallback<V, I>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflateViewBinding(inflater, parent)
        return BaseViewHolder(binding)
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater, parent: ViewGroup): V

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        val item = getItem(position)
        item.bind(holder)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<V>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        item.bind(holder, payloads)
    }

    override fun onViewRecycled(holder: BaseViewHolder<V>) {
        super.onViewRecycled(holder)
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            getItem(holder.adapterPosition)?.unbind(holder)
        }
    }

    override fun getItemId(position: Int): Long = getItem(position).uniqueId

    override fun getItemViewType(position: Int): Int = getItem(position).layoutId.res

    fun newSpanSizeLookup(): GridLayoutManager.SpanSizeLookup {
        return object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return getItem(position).spanSize
            }
        }
    }
}