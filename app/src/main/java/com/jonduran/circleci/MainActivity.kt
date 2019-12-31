package com.jonduran.circleci

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.data.Repository
import com.jonduran.circleci.databinding.ActivityMainBinding
import com.jonduran.circleci.databinding.ActivityMainBinding.inflate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var repository: Repository
    private lateinit var binding: ActivityMainBinding
    internal lateinit var component: MainComponent

    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory(repository) }

    init {
        lifecycleScope.launchWhenCreated {
            component = inject()
            binding = inflate(layoutInflater)
            setContentView(binding.root)
            viewModel.state.onEach { render(it) }.launchIn(this)
            viewModel.process(MainViewModel.Action.LoadData)
        }
    }

    private fun render(state: MainViewModel.State) {
        when (state) {
            is MainViewModel.State.Loading -> {}
            is MainViewModel.State.Success -> goToProjectListScreen()
            is MainViewModel.State.Unauthorized -> goToKeyEntryScreen()
            is MainViewModel.State.Failure -> {}
        }
    }

    fun goToKeyEntryScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, KeyEntryFragment())
        }
    }

    fun goToProjectListScreen() {
        supportFragmentManager.commit {
            replace(R.id.content, BuildListFragment())
        }
    }

    private fun MainActivity.inject(): MainComponent {
        return CircleCiApp.component.mainComponent()
            .create()
            .apply { inject(this@inject) }
    }
}
