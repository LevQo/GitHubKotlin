package com.levqo.githubkotlin.domain.repositories.repository

import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import com.levqo.githubkotlin.data.network.ApiResult

interface RepositoriesRepository {
    suspend fun getGitHubRepositories(lastRepoId: Int?): ApiResult<List<GitHubRepositoryModel>?>
}