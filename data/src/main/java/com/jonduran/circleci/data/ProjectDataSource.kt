package com.jonduran.circleci.data

import com.jonduran.circleci.cache.ProjectEntity
import com.jonduran.circleci.cache.SourceControl

interface ProjectDataSource {
    suspend fun getListOfProjects(
        sourceControl: Array<SourceControl>,
        username: String
    ): List<ProjectEntity>
    suspend fun getListOfProjectOwners(): List<String>
    suspend fun save(project: ProjectEntity)
    suspend fun saveAll(projects: List<ProjectEntity>)
}