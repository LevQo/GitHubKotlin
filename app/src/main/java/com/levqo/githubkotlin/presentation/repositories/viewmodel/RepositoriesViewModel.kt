package com.levqo.githubkotlin.presentation.repositories.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.levqo.githubkotlin.data.network.ApiResult
import com.levqo.githubkotlin.domain.repositories.use_case.RepositoriesInteractor
import com.levqo.githubkotlin.presentation.repositories.models.RepositoriesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoriesViewModel(app: Application, private val interactor: RepositoriesInteractor) :
    AndroidViewModel(app) {

    private var lastRepoId: Int? = null
    var isCache = false

    private val liveData = MutableLiveData<RepositoriesState>()
    fun getLiveData(): LiveData<RepositoriesState> = liveData

    fun loadRepositories(isRefresh: Boolean = false) {
        if (isRefresh)
            lastRepoId = null

        if (lastRepoId == null)
            liveData.value = RepositoriesState.Loading
        else
            liveData.value = RepositoriesState.LoadingNextPage

        viewModelScope.launch(Dispatchers.IO) {
            when (val result = interactor.getGitHubRepositories(lastRepoId)) {
                is ApiResult.NetworkError -> {
                    liveData.postValue(RepositoriesState.Error(result.getError(getApplication())))
                }
                is ApiResult.Success -> {
                    isCache = result.isCache
                    result.value?.let { repositories ->
                        lastRepoId = repositories.last().id
                        liveData.postValue(
                            RepositoriesState.Success(
                                repositories,
                                isRefresh,
                                isCache = result.isCache
                            )
                        )
                    }
                }
            }
        }
    }
}