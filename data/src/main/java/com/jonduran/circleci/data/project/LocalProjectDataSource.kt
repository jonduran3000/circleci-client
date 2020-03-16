package com.jonduran.circleci.data.project

import com.jonduran.circleci.cache.AppDatabase
import com.jonduran.circleci.cache.dao.ProjectDao
import com.jonduran.circleci.cache.entity.ProjectEntity
import com.jonduran.circleci.cache.entity.SourceControl
import javax.inject.Inject

class LocalProjectDataSource @Inject constructor(database: AppDatabase) : ProjectDataSource {
    private val dao: ProjectDao = database.projectDao()

    override suspend fun getListOfProjects(
        sourceControl: Array<SourceControl>,
        username: String
    ): List<ProjectEntity> {
        return dao.getProjects(username, sourceControl)
    }

    override suspend fun getListOfProjectOwners(): List<String> {
        return dao.getProjectOwnerNames()
    }

    override suspend fun save(project: ProjectEntity) {
        dao.insert(project)
    }

    override suspend fun saveAll(projects: List<ProjectEntity>) {
        dao.insertAll(projects)
    }
}