package com.jonduran.circleci

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.build.BuildListFragment
import com.jonduran.circleci.common.ui.activity.InjectingActivity
import com.jonduran.circleci.common.ui.utils.viewBinding
import com.jonduran.circleci.databinding.ActivityMainBinding
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import com.jonduran.circleci.utils.exhaustive
import javax.inject.Inject

class MainActivity : InjectingActivity() {
    @Inject lateinit var factory: MainViewModel.Factory
    private val viewModel by viewModels<MainViewModel> { factory }
    private val binding by viewBinding(ActivityMainBinding::inflate)

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.state.observe(this, ::render)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun render(state: MainViewModel.State) {
        Log.d("MainActivity", state.toString())
        when (state) {
            is MainViewModel.State.Loading -> {}
            is MainViewModel.State.SavedInstance -> {}
            is MainViewModel.State.Success -> goToBuildListScreen()
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

    fun goToBuildListScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, BuildListFragment())
        }
    }
}
