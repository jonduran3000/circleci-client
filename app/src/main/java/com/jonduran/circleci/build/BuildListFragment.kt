package com.jonduran.circleci.build

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonduran.circleci.R
import com.jonduran.circleci.common.ui.list.BaseAdapter
import com.jonduran.circleci.common.ui.list.Item
import com.jonduran.circleci.common.ui.utils.viewBinding
import com.jonduran.circleci.databinding.FragmentBuildListBinding
import com.jonduran.circleci.utils.exhaustive
import com.jonduran.circleci.viewmodel.InjectedViewModelFactory
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BuildListFragment @Inject constructor(
    private val factoryProducer: InjectedViewModelFactory.Producer
) : Fragment(R.layout.fragment_build_list) {
    private val viewModel by viewModels<BuildListViewModel> {
        factoryProducer.produce(this, arguments)
    }
    private val binding by viewBinding(FragmentBuildListBinding::bind)
    private val adapter = BaseAdapter<Item>()

    init {
        viewLifecycleOwnerLiveData.observe(this) { owner ->
            owner.lifecycleScope.launchWhenCreated {
                binding.root.let { recyclerView ->
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter
                }
                viewModel.state.onEach { state -> render(state) }.launchIn(this)
            }
        }
    }

    private fun render(state: BuildListViewModel.State) {
        when (state) {
            BuildListViewModel.State.Loading -> {
            }
            is BuildListViewModel.State.Success -> {
                adapter.submitList(state.builds)
            }
            is BuildListViewModel.State.Failure -> {
                val log = Log.e("BuildListFragment", "Error:", state.error)
            }
        }.exhaustive
    }
}