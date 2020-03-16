package com.jonduran.circleci.data.project

import com.jonduran.circleci.cache.entity.ProjectEntity
import com.jonduran.circleci.cache.entity.SourceControl
import com.jonduran.circleci.remote.CircleCiApi
import javax.inject.Inject

class RemoteProjectDataSource @Inject constructor(
    private val api: CircleCiApi
) : ProjectDataSource {
    override suspend fun getListOfProjects(
        sourceControl: Array<SourceControl>,
        username: String
    ): List<ProjectEntity> {
        return api.getListOfProjects().map { project ->
            ProjectEntity(
                name = project.name,
                username = project.username,
                type = SourceControl.valueOf(project.type),
                url = project.url
            )
        }
    }

    override suspend fun getListOfProjectOwners(): List<String> {
        throw UnsupportedOperationException()
    }

    override suspend fun save(project: ProjectEntity) {
        throw UnsupportedOperationException()
    }

    override suspend fun saveAll(projects: List<ProjectEntity>) {
        throw UnsupportedOperationException()
    }
}