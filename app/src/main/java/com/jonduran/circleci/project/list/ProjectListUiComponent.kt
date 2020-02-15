package com.jonduran.circleci.project.list

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.snackbar.Snackbar
import com.jonduran.circleci.R
import com.jonduran.circleci.UiComponent
import com.jonduran.circleci.cache.SourceControl
import com.jonduran.circleci.common.ui.list.BaseAdapter
import com.jonduran.circleci.databinding.FragmentProjectListBinding
import com.jonduran.circleci.extensions.float
import com.jonduran.circleci.utils.exhaustive

class ProjectListUiComponent(
    binding: FragmentProjectListBinding,
    private val onVersionControlChange: (Array<SourceControl>) -> Unit,
    private val onOrganizationChange: (String) -> Unit
) : UiComponent<FragmentProjectListBinding, ProjectListUiComponent.State>(binding) {
    private lateinit var orgDropdownAdapter: ArrayAdapter<String>
    private val adapter = BaseAdapter<ProjectItem>()

    init {
        setUpProjectList()
        setUpVersionControlDropdown()
        setUpOrganizationDropdown()
        binding.sheet.background = createRoundedTopBackground(binding.sheet.context)
    }

    private fun setUpProjectList() {
        binding.projectList.let { list ->
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(list.context)
        }
    }

    private fun setUpVersionControlDropdown() {
        binding.versionControlDropdown.let { dropdown ->
            val adapter = ArrayAdapter(
                binding.root.context,
                R.layout.list_item_backdrop,
                SourceControl.values()
            )
            dropdown.setAdapter(adapter)
            dropdown.setOnItemClickListener { parent, _, position, _ ->
                val value = parent.getItemAtPosition(position) as SourceControl
                onVersionControlChange(arrayOf(value))
            }
        }
    }

    private fun setUpOrganizationDropdown() {
        orgDropdownAdapter = ArrayAdapter(binding.root.context,
            R.layout.list_item_backdrop
        )
        binding.organizationDropdown.let { dropdown ->
            dropdown.setAdapter(orgDropdownAdapter)
            dropdown.setOnItemClickListener { parent, _, position, _ ->
                val value = parent.getItemAtPosition(position) as String
                onOrganizationChange(value)
            }
        }
    }

    private fun createRoundedTopBackground(context: Context): Drawable {
        val model = ShapeAppearanceModel
            .builder(context, R.style.ShapeAppearance_Stable_Sheet, 0)
            .build()
        return MaterialShapeDrawable(model).apply {
            fillColor = ColorStateList.valueOf(Color.WHITE)
        }
    }

    override fun render(state: State) {
        when (state) {
            State.Loading -> {
                Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).float()
            }
            is State.Success -> adapter.submitList(state.projects)
            is State.Failure -> {
                Snackbar.make(binding.root, state.toString(), Snackbar.LENGTH_SHORT).float()
            }
        }.exhaustive
    }

    sealed class State {
        object Loading : State()
        data class Success(val projects: List<ProjectItem>) : State()
        data class Failure(val error: Throwable) : State()
    }
}