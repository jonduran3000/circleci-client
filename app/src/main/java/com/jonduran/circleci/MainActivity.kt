package com.jonduran.circleci

import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.data.UserRepository
import com.jonduran.circleci.databinding.ActivityMainBinding
import com.jonduran.circleci.databinding.ActivityMainBinding.inflate
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.key.KeyEntryFragment
import com.jonduran.circleci.project.list.ProjectListFragment
import com.jonduran.circleci.utils.exhaustive
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var repository: UserRepository
    private lateinit var binding: ActivityMainBinding
    private var component: MainComponent? = null

    private val viewModel by viewModels<MainViewModel> {
        MainViewModel.Factory(repository, this)
    }

    init {
        lifecycleScope.launchWhenCreated {
            getComponent().inject(this@MainActivity)
            binding = inflate(layoutInflater)
            setContentView(binding.root)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.observe(this) { state ->
                render(state)
            }
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

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return component
    }

    fun getComponent(): MainComponent {
        return component
            ?: lastNonConfigurationInstance as? MainComponent
            ?: CircleCiApp.component.mainComponent().create()
    }
}
