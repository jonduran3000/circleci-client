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
import com.jonduran.circleci.cache.entity.SourceControl
import com.jonduran.circleci.common.ui.list.BaseAdapter
import com.jonduran.circleci.databinding.FragmentProjectListBinding
import com.jonduran.circleci.extensions.float
import com.jonduran.circleci.utils.exhaustive

fun FragmentProjectListBinding.setUp(
    onVersionControlChange: (Array<SourceControl>) -> Unit,
    onOrganizationChange: (String) -> Unit
) {
    setUpProjectList()
    setUpVersionControlDropdown(onVersionControlChange)
    setUpOrganizationDropdown(onOrganizationChange)
    sheet.background = createRoundedTopBackground(sheet.context)
}

private fun FragmentProjectListBinding.setUpProjectList() {
    projectList.let { list ->
        list.adapter = BaseAdapter<ProjectItem>()
        list.layoutManager = LinearLayoutManager(list.context)
    }
}

private fun FragmentProjectListBinding.setUpVersionControlDropdown(
    onVersionControlChange: (Array<SourceControl>) -> Unit
) {
    versionControlDropdown.let { dropdown ->
        val adapter = ArrayAdapter(root.context, R.layout.list_item_backdrop, SourceControl.values())
        dropdown.setAdapter(adapter)
        dropdown.setOnItemClickListener { parent, _, position, _ ->
            val value = parent.getItemAtPosition(position) as SourceControl
            onVersionControlChange(arrayOf(value))
        }
    }
}

private fun FragmentProjectListBinding.setUpOrganizationDropdown(
    onOrganizationChange: (String) -> Unit
) {
    val adapter = ArrayAdapter<String>(root.context, R.layout.list_item_backdrop)
    organizationDropdown.let { dropdown ->
        dropdown.setAdapter(adapter)
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

fun FragmentProjectListBinding.render(state: State) {
    when (state) {
        State.Loading -> {
            Snackbar.make(root, "Loading", Snackbar.LENGTH_SHORT).float()
        }
        is State.Success -> {
            (projectList.adapter as BaseAdapter<ProjectItem>).submitList(state.projects)
        }
        is State.Failure -> {
            Snackbar.make(root, state.toString(), Snackbar.LENGTH_SHORT).float()
        }
    }.exhaustive
}

sealed class State {
    object Loading : State()
    data class Success(val projects: List<ProjectItem>) : State()
    data class Failure(val error: Throwable) : State()
}