package com.jonduran.circleci.project.list

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.jonduran.circleci.R
import com.jonduran.circleci.common.ui.fragment.BaseFragment
import com.jonduran.circleci.common.ui.utils.Injectable
import com.jonduran.circleci.common.ui.utils.autoCleared
import com.jonduran.circleci.common.ui.utils.viewBinding
import com.jonduran.circleci.databinding.FragmentProjectListBinding
import javax.inject.Inject

class ProjectListFragment : BaseFragment(R.layout.fragment_project_list), Injectable {
    @Inject lateinit var factory: ProjectListViewModel.Factory
    private val viewModel by viewModels<ProjectListViewModel> { factory }
    private val binding by viewBinding(FragmentProjectListBinding::bind)
    private var uiComponent by autoCleared<ProjectListUiComponent>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        uiComponent = ProjectListUiComponent(
            binding = binding,
            onVersionControlChange = { vcs -> viewModel.versionControl.setValue(vcs) },
            onOrganizationChange = { org -> viewModel.organization.setValue(org) }
        )

        viewModel.state.observe(this, uiComponent::render)
    }
}