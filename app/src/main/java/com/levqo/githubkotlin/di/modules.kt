package com.levqo.githubkotlin.di

import com.levqo.githubkotlin.data.db.MainDataBase
import com.levqo.githubkotlin.data.network.RetrofitService.getClient
import com.levqo.githubkotlin.data.network.RetrofitService.getGitHubApi
import com.levqo.githubkotlin.data.network.RetrofitService.getRetrofitBuilder
import com.levqo.githubkotlin.data.repository.repositories.RepositoriesRepositoryImpl
import com.levqo.githubkotlin.domain.repositories.repository.RepositoriesRepository
import com.levqo.githubkotlin.domain.repositories.use_case.RepositoriesInteractor
import com.levqo.githubkotlin.domain.repositories.use_case.RepositoriesInteractorImpl
import com.levqo.githubkotlin.presentation.repositories.viewmodel.RepositoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module {
    viewModel { RepositoriesViewModel(get(), get()) }
    factory<RepositoriesInteractor> { RepositoriesInteractorImpl(get()) }
    factory<RepositoriesRepository> { RepositoriesRepositoryImpl(get(), get()) }
    single { MainDataBase.create(get()) }
    single { getRetrofitBuilder() }
    single { getClient() }
    single { getGitHubApi(get(), get()) }
}