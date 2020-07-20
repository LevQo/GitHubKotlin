package com.levqo.githubkotlin.data.db.repositories

import androidx.room.Entity
import com.levqo.githubkotlin.data.models.GitHubRepositoryModel

@Entity(tableName = "repositories", primaryKeys = ["id"])
data class RepositoryEntity(
    val id: Int,
    val name: String,
    val description: String?,
    val ownerLogin: String
)