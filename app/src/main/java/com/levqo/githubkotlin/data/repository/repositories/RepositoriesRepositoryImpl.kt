package com.levqo.githubkotlin.data.repository.repositories

import com.levqo.githubkotlin.data.db.MainDataBase
import com.levqo.githubkotlin.data.db.repositories.RepositoryEntity
import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import com.levqo.githubkotlin.data.network.ApiResult
import com.levqo.githubkotlin.data.network.ApiService
import com.levqo.githubkotlin.data.network.safeApiCall
import com.levqo.githubkotlin.domain.repositories.repository.RepositoriesRepository

class RepositoriesRepositoryImpl(private val apiService: ApiService, private val db: MainDataBase) :
    RepositoriesRepository {

    override suspend fun getGitHubRepositories(lastRepoId: Int?): ApiResult<List<GitHubRepositoryModel>?> {
        return when (val result = safeApiCall { apiService.getGitHubRepositories(lastRepoId) }) {
            is ApiResult.Success -> {
                val cacheResult = db.repositoriesDao.loadRepositories() as ArrayList
                db.repositoriesDao.saveRepositories(cacheResult.also {
                    it.addAll(
                        mapToEntity(
                            result.value ?: listOf()
                        )
                    )
                })
                result
            }
            is ApiResult.NetworkError -> {
                val cacheResult = db.repositoriesDao.loadRepositories()
                if (!cacheResult.isNullOrEmpty()) {
                    ApiResult.Success(mapToModel(cacheResult), isCache = true)
                } else {
                    result
                }
            }
        }
    }

    private fun mapToEntity(list: List<GitHubRepositoryModel>): List<RepositoryEntity> {
        return list.map {
            RepositoryEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                ownerLogin = it.owner.login
            )
        }
    }

    private fun mapToModel(list: List<RepositoryEntity>): List<GitHubRepositoryModel>? {
        return list.map {
            GitHubRepositoryModel(
                id = it.id,
                name = it.name,
                description = it.description,
                owner = GitHubRepositoryModel.OwnerModel(login = it.ownerLogin)
            )
        }
    }
}