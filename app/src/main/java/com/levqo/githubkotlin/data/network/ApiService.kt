package com.levqo.githubkotlin.data.network

import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/repositories")
    suspend fun getGitHubRepositories(@Query("since") lastRepoId: Int?): List<GitHubRepositoryModel>

    @GET("/repos/{owner}/{repo}")
    suspend fun getRepositoryDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<GitHubRepositoryModel>

    @GET("/users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String
    ): List<GitHubRepositoryModel>

}