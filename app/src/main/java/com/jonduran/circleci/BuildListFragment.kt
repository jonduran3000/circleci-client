package com.jonduran.circleci

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.common.ui.list.BaseAdapter
import com.jonduran.circleci.data.Repository
import com.jonduran.circleci.databinding.FragmentBuildListBinding
import com.jonduran.circleci.databinding.ListItemProjectBinding
import javax.inject.Inject

class BuildListFragment : Fragment() {
    @Inject lateinit var repository: Repository
    private lateinit var binding: FragmentBuildListBinding
    private lateinit var component: BuildListComponent
    private lateinit var vcsDropdownAdapter: ArrayAdapter<SourceControl>
    private lateinit var orgDropdownAdapter: ArrayAdapter<String>

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
            vcsDropdownAdapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item_backdrop,
                SourceControl.values()
            )
            orgDropdownAdapter = ArrayAdapter(
                requireContext(),
                R.layout.list_item_backdrop
            )
        }

        lifecycleScope.launchWhenStarted {
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
            binding.projectList.let { list ->
                list.adapter = adapter
                list.layoutManager = LinearLayoutManager(requireContext())
            }
            binding.versionControlDropdown.let { dropdown ->
                dropdown.setAdapter(vcsDropdownAdapter)
                dropdown.setOnItemClickListener { parent, _, position, _ ->
                    val value = parent.getItemAtPosition(position) as SourceControl
                    viewModel.value.versionControl.value = arrayOf(value)
                }
            }
            binding.organizationDropdown.setAdapter(orgDropdownAdapter)
            binding.sheet.background = createRoundedTopBackground(binding.sheet.context)
            viewModel.value.state.observe(this@BuildListFragment) { state ->
                render(state)
            }
        }
    }

    private fun createRoundedTopBackground(context: Context): Drawable {
        val model = ShapeAppearanceModel
            .builder(context, R.style.ShapeAppearance_Stable_Sheet, 0)
            .build()
        return MaterialShapeDrawable(model).apply {
            fillColor = ColorStateList.valueOf(Color.WHITE)
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