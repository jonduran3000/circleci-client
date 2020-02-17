package com.jonduran.circleci.key

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.jonduran.circleci.MainActivity
import com.jonduran.circleci.common.ui.fragment.Inflate
import com.jonduran.circleci.common.ui.fragment.InjectedFragment
import com.jonduran.circleci.databinding.FragmentKeyEntryBinding
import com.jonduran.circleci.databinding.FragmentKeyEntryBinding.inflate
import com.jonduran.circleci.extensions.float
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.utils.exhaustive
import javax.inject.Inject

class KeyEntryFragment : InjectedFragment<FragmentKeyEntryBinding>() {
    @Inject lateinit var factory: KeyEntryViewModel.Factory
    private val viewModel by viewModels<KeyEntryViewModel> { factory }

    override val inflateBinding: Inflate<FragmentKeyEntryBinding>
        get() = { inflater, container, attachToRoot ->
            inflate(inflater, container, attachToRoot)
        }

    init {
        lifecycleScope.launchWhenStarted {
            binding.submitButton.setOnClickListener {
                val text = binding.keyInput.text
                val action = KeyEntryViewModel.Action.OnSubmitClicked(text)
                viewModel.process(action)
            }
            viewModel.state.observe(this, ::render)
        }
    }

    private fun render(state: KeyEntryViewModel.State) {
        when (state) {
            is KeyEntryViewModel.State.EmptyKey -> {
                binding.keyLayout.error = "Please enter your API key"
            }
            is KeyEntryViewModel.State.InvalidKey -> {
                binding.keyLayout.error = "Please enter a valid API key"
            }
            is KeyEntryViewModel.State.Success -> {
                (requireActivity() as MainActivity).goToProjectListScreen()
            }
            is KeyEntryViewModel.State.Failure -> {
                Snackbar.make(binding.root, state.toString(), Snackbar.LENGTH_LONG).float()
            }
        }.exhaustive
    }
}