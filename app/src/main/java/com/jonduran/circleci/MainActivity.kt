package com.jonduran.circleci

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.build.BuildListFragment
import com.jonduran.circleci.common.ui.utils.viewBinding
import com.jonduran.circleci.databinding.ActivityMainBinding
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import com.jonduran.circleci.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.state.observe(this, ::render)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
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
            replace(R.id.content, KeyEntryFragment::class.java, null)
        }
    }

    fun goToProjectListScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, ProjectListFragment::class.java, null)
        }
    }

    fun goToBuildListScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, BuildListFragment::class.java, null)
        }
    }
}
