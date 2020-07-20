package com.levqo.githubkotlin.domain.repositories.use_case

import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import com.levqo.githubkotlin.data.network.ApiResult

interface RepositoriesInteractor {
    suspend fun getGitHubRepositories(lastRepoId: Int?): ApiResult<List<GitHubRepositoryModel>?>
}