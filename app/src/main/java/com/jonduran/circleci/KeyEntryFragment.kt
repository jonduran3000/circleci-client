package com.jonduran.circleci

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jonduran.circleci.data.Repository
import com.jonduran.circleci.databinding.FragmentKeyEntryBinding
import kotlinx.android.synthetic.main.fragment_key_entry.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class KeyEntryFragment : Fragment() {
    @Inject lateinit var repository: Repository
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
                viewModel.storeApiKey(binding.keyInput.text)
            }
            viewModel.state.onEach { render(it) }.launchIn(this)
        }
    }

    private fun render(state: KeyEntryViewModel.State) {
        when (state) {
            is KeyEntryViewModel.State.EmptyKey -> {
                Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
            }
            is KeyEntryViewModel.State.Success -> {
                (requireActivity() as MainActivity).goToProjectListScreen()
            }
            is KeyEntryViewModel.State.Failure -> {
                Toast.makeText(requireContext(), state.toString(), Toast.LENGTH_LONG).show()
            }
        }
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
        return (requireActivity() as MainActivity).component
            .keyEntryComponent()
            .create()
            .apply { inject(this@inject) }
    }
}