package com.levqo.githubkotlin.domain.repositories.use_case

import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import com.levqo.githubkotlin.data.network.ApiResult
import com.levqo.githubkotlin.domain.repositories.repository.RepositoriesRepository

class RepositoriesInteractorImpl(private val repository: RepositoriesRepository) :
    RepositoriesInteractor {

    override suspend fun getGitHubRepositories(lastRepoId: Int?): ApiResult<List<GitHubRepositoryModel>?> {
        return repository.getGitHubRepositories(lastRepoId)
    }
}