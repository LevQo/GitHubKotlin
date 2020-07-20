package com.levqo.githubkotlin.data.models

data class GitHubRepositoryModel(
    val id: Int,
    val name: String,
    val description: String?,
    val owner: OwnerModel
) {
    data class OwnerModel(
        val login: String
    )
}

