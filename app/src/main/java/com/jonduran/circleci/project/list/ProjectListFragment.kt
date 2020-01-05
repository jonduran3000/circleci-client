package com.jonduran.circleci.project.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.jonduran.circleci.BuildListComponent
import com.jonduran.circleci.MainActivity
import com.jonduran.circleci.data.ProjectRepository
import com.jonduran.circleci.databinding.FragmentProjectListBinding
import com.jonduran.circleci.databinding.FragmentProjectListBinding.inflate
import javax.inject.Inject

class ProjectListFragment : Fragment() {
    @Inject lateinit var projectRepository: ProjectRepository
    private lateinit var binding: FragmentProjectListBinding
    private lateinit var uiComponent: ProjectListComponent
    private lateinit var component: BuildListComponent

    private val viewModel by viewModels<ProjectListViewModel> {
        ProjectListViewModel.Factory(projectRepository, this)
    }

    init {
        lifecycleScope.launchWhenCreated { component = inject() }

        lifecycleScope.launchWhenStarted {
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
            viewModel.state.observe(this@ProjectListFragment) { state ->
                uiComponent.render(state)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, container, false)
        uiComponent = ProjectListComponent(
            binding = binding,
            onVersionControlChange = { viewModel.versionControl.setValue(it) },
            onOrganizationChange = { viewModel.organization.setValue(it) }
        )
        return binding.root
    }

    private fun ProjectListFragment.inject(): BuildListComponent {
        return (requireActivity() as MainActivity).getComponent()
            .buildListComponent()
            .create()
            .apply { inject(this@inject) }
    }
}