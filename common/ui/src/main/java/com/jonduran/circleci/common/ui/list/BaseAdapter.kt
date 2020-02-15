package com.jonduran.circleci.common.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<I : Item> : ListAdapter<I, BaseViewHolder>(
    ItemDiffCallback<I>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(inflater.inflate(viewType, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        item.bind(holder)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val item = getItem(position)
        item.bind(holder, payloads)
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
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