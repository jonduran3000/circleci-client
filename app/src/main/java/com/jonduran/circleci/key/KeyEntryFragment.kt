package com.jonduran.circleci.key

import android.util.Log
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.jonduran.circleci.MainActivity
import com.jonduran.circleci.R
import com.jonduran.circleci.common.ui.fragment.BaseFragment
import com.jonduran.circleci.common.ui.utils.viewBinding
import com.jonduran.circleci.databinding.FragmentKeyEntryBinding
import com.jonduran.circleci.extensions.float
import com.jonduran.circleci.extensions.launchWhenViewCreated
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KeyEntryFragment : BaseFragment(R.layout.fragment_key_entry) {
    private val viewModel by viewModels<KeyEntryViewModel>()
    private val binding by viewBinding(FragmentKeyEntryBinding::bind)

    init {
        launchWhenViewCreated {
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
                (requireActivity() as MainActivity).goToBuildListScreen()
            }
            is KeyEntryViewModel.State.Failure -> {
                Log.e("KeyEntryFragment", "Error:", state.error)
                Snackbar.make(binding.root, state.toString(), Snackbar.LENGTH_LONG).float()
            }
            KeyEntryViewModel.State.Uninitialized -> {}
        }.exhaustive
    }
}