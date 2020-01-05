package com.jonduran.circleci.key

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.KeyEntryComponent
import com.jonduran.circleci.MainActivity
import com.jonduran.circleci.data.UserRepository
import com.jonduran.circleci.databinding.FragmentKeyEntryBinding
import com.jonduran.circleci.extensions.observe
import com.jonduran.circleci.utils.exhaustive
import kotlinx.android.synthetic.main.fragment_key_entry.*
import javax.inject.Inject

class KeyEntryFragment : Fragment() {
    @Inject lateinit var repository: UserRepository
    private lateinit var binding: FragmentKeyEntryBinding
    private lateinit var component: KeyEntryComponent

    private val viewModel by viewModels<KeyEntryViewModel> {
        KeyEntryViewModel.Factory(repository)
    }

    init {
        lifecycleScope.launchWhenCreated {
            component = inject()
        }

        lifecycleScope.launchWhenStarted {
            submitButton.setOnClickListener {
                val text = binding.keyInput.text
                val action = KeyEntryViewModel.Action.OnSubmitClicked(text)
                viewModel.process(action)
            }
            viewModel.state.observe(this) { state ->
                render(state)
            }
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
                Toast.makeText(requireContext(), state.toString(), Toast.LENGTH_LONG).show()
            }
        }.exhaustive
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKeyEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun KeyEntryFragment.inject(): KeyEntryComponent {
        return (requireActivity() as MainActivity).getComponent()
            .keyEntryComponent()
            .create()
            .apply { inject(this@inject) }
    }
}