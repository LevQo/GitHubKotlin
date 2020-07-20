package com.levqo.githubkotlin.domain.repositories.use_case

import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import com.levqo.githubkotlin.data.network.ApiResult
import com.levqo.githubkotlin.domain.repositories.repository.RepositoriesRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoriesInteractorImplTest {

    @Mock
    private lateinit var mockRepository: RepositoriesRepository
    private lateinit var interactor: RepositoriesInteractor

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = RepositoriesInteractorImpl(repository = mockRepository)
    }

    @Test
    fun getGitHubRepositories_Success() = runBlocking {
        // arrange
        `when`(mockRepository.getGitHubRepositories(null)).thenReturn(
            ApiResult.Success(
                value = listOf()
            )
        )
        // act
        val result = interactor.getGitHubRepositories(null)
        // assert
        verify(mockRepository).getGitHubRepositories(null)
        assertEquals(result, ApiResult.Success<List<GitHubRepositoryModel>>(value = listOf()))
    }

    @Test
    fun getGitHubRepositories_Error() = runBlocking {
        // arrange
        `when`(mockRepository.getGitHubRepositories(null)).thenReturn(ApiResult.NetworkError())
        // act
        val result = interactor.getGitHubRepositories(null)
        // assert
        verify(mockRepository).getGitHubRepositories(null)
        assertEquals(result, ApiResult.NetworkError())
    }
}