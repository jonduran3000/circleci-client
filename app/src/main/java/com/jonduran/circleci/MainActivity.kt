package com.jonduran.circleci

import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.common.ui.activity.InjectingActivity
import com.jonduran.circleci.databinding.ActivityMainBinding
import com.jonduran.circleci.databinding.ActivityMainBinding.inflate
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import com.jonduran.circleci.utils.exhaustive
import javax.inject.Inject

class MainActivity : InjectingActivity<ActivityMainBinding>() {
    @Inject lateinit var factory: MainViewModel.Factory
    private val viewModel by viewModels<MainViewModel> { factory }

    override val inflateBinding: (LayoutInflater) -> ActivityMainBinding = ::inflate

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.state.observe(this, ::render)
        }
    }

    private fun render(state: MainViewModel.State) {
        Log.d("MainActivity", state.toString())
        when (state) {
            is MainViewModel.State.Loading -> {}
            is MainViewModel.State.SavedInstance -> {}
            is MainViewModel.State.Success -> goToProjectListScreen()
            is MainViewModel.State.Unauthorized -> goToKeyEntryScreen()
            is MainViewModel.State.Failure -> {}
        }.exhaustive
    }

    fun goToKeyEntryScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, KeyEntryFragment())
        }
    }

    fun goToProjectListScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, ProjectListFragment())
        }
    }
}
