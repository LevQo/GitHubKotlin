package com.levqo.githubkotlin.data.models

import com.google.gson.annotations.SerializedName
import com.levqo.githubkotlin.domain.repositories.entity.GitHubRepositoryEntity

data class GitHubRepositoryModel(
    @SerializedName("name") override val name: String,
    @SerializedName("description") override val description: String
) : GitHubRepositoryEntity(name, description)