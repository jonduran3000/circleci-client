package com.jonduran.circleci

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.jonduran.circleci.common.list.BaseAdapter
import com.jonduran.circleci.data.Repository
import com.jonduran.circleci.databinding.FragmentBuildListBinding
import com.jonduran.circleci.databinding.ListItemProjectBinding
import javax.inject.Inject

class BuildListFragment : Fragment() {
    private lateinit var binding: FragmentBuildListBinding
    private lateinit var component: BuildListComponent
    @Inject lateinit var repository: Repository
    private val viewModel = viewModels<BuildListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return BuildListViewModel(repository) as T
            }
        }
    }
    private val adapter = object : BaseAdapter<ListItemProjectBinding, ProjectItem>() {
        override fun inflateViewBinding(
            inflater: LayoutInflater,
            parent: ViewGroup
        ): ListItemProjectBinding {
            return ListItemProjectBinding.inflate(inflater, parent, false)
        }
    }

    init {
        lifecycleScope.launchWhenCreated {
            component = inject()
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
            binding.buildList.adapter = adapter
            binding.buildList.layoutManager = LinearLayoutManager(requireContext())
            binding.sheet.background = MaterialShapeDrawable(
                ShapeAppearanceModel.builder(
                    binding.sheet.context,
                    R.style.ShapeAppearance_Stable_Sheet,
                    0
                ).build()
            ).apply {
                fillColor = ColorStateList.valueOf(Color.WHITE)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.value.state.observe(this@BuildListFragment) { render(it) }
            viewModel.value.process(Action.InitialLoad)
        }
    }

    private fun render(state: State<List<ProjectItem>>) {
        when (state) {
            is State.Loading -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
            }
            is State.Success -> {
                adapter.submitList(state.data)
            }
            is State.Failure -> {
                Toast.makeText(requireContext(), state.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuildListBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun BuildListFragment.inject(): BuildListComponent {
        return (requireActivity() as MainActivity).component
            .buildListComponent()
            .create()
            .apply { inject(this@inject) }
    }
}