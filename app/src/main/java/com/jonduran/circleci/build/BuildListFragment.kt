package com.jonduran.circleci.build

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonduran.circleci.R
import com.jonduran.circleci.common.ui.list.BaseAdapter
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.databinding.FragmentBuildListBinding
import com.jonduran.circleci.extensions.launchWhenViewCreated
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.viewmodel.InjectedViewModelFactory
import javax.inject.Inject

class BuildListFragment @Inject constructor(
    private val factoryProducer: InjectedViewModelFactory.Producer
) : Fragment(R.layout.fragment_build_list) {
    private val viewModel by viewModels<BuildListViewModel> {
        factoryProducer.produce(this, arguments)
    }

    init {
        launchWhenViewCreated {
            val binding = FragmentBuildListBinding.bind(requireView())
            binding.root.let { recyclerView ->
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = BaseAdapter<Item>()
            }
            viewModel.state.observe(this, binding::render)
        }
    }
}