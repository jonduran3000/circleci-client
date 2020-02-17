package com.jonduran.circleci.project.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.jonduran.circleci.common.ui.fragment.Inflate
import com.jonduran.circleci.common.ui.fragment.InjectedFragment
import com.jonduran.circleci.databinding.FragmentProjectListBinding
import com.jonduran.circleci.databinding.FragmentProjectListBinding.inflate
import javax.inject.Inject

class ProjectListFragment : InjectedFragment<FragmentProjectListBinding>() {
    @Inject lateinit var factory: ProjectListViewModel.Factory
    private val viewModel by viewModels<ProjectListViewModel> { factory }

    private var uiComponent: ProjectListUiComponent? = null

    override val inflateBinding: Inflate<FragmentProjectListBinding>
        get() = { inflater, container, attachToRoot ->
            inflate(inflater, container, attachToRoot)
        }

    init {
        lifecycleScope.launchWhenStarted {
            (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
            viewModel.state.observe(this@ProjectListFragment) { state ->
                uiComponent?.render(state)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uiComponent = ProjectListUiComponent(
            binding = binding,
            onVersionControlChange = { vcs -> viewModel.versionControl.setValue(vcs) },
            onOrganizationChange = { org -> viewModel.organization.setValue(org) }
        )
    }

    override fun onDestroyView() {
        uiComponent = null
        super.onDestroyView()
    }
}