package com.jonduran.circleci.build

import android.util.Log
import com.jonduran.circleci.common.ui.list.BaseAdapter
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.databinding.FragmentBuildListBinding
import com.jonduran.circleci.utils.exhaustive

fun FragmentBuildListBinding.render(state: BuildListViewModel.State) {
    when (state) {
        BuildListViewModel.State.Loading -> {
        }
        is BuildListViewModel.State.Success -> {
            (root.adapter as BaseAdapter<Item>).submitList(state.builds)
        }
        is BuildListViewModel.State.Failure -> {
            val log = Log.e("BuildListFragment", "Error:", state.error)
        }
    }.exhaustive
}