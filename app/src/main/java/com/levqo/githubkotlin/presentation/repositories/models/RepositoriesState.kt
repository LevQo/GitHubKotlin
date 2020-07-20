package com.levqo.githubkotlin.presentation.repositories.models

import com.levqo.githubkotlin.data.models.GitHubRepositoryModel

sealed class RepositoriesState {
    object Loading : RepositoriesState()
    object LoadingNextPage : RepositoriesState()
    data class Success(
        val repositories: List<GitHubRepositoryModel>,
        val isRefresh: Boolean = false,
        val isCache: Boolean = false
    ) : RepositoriesState()

    data class Error(val message: String?) : RepositoriesState()
}