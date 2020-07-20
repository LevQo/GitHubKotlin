package com.levqo.githubkotlin.presentation.repositories.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.levqo.githubkotlin.R
import com.levqo.githubkotlin.presentation.repositories.models.RepositoriesState
import com.levqo.githubkotlin.presentation.repositories.view.adapter.RepositoriesAdapter
import com.levqo.githubkotlin.presentation.repositories.viewmodel.RepositoriesViewModel
import kotlinx.android.synthetic.main.fragment_repositories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesFragment : Fragment(R.layout.fragment_repositories),
    SwipeRefreshLayout.OnRefreshListener {

    private val navController by lazy { findNavController() }

    private val viewModel: RepositoriesViewModel by viewModel()
    lateinit var adapter: RepositoriesAdapter

    private var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout?.setOnRefreshListener(this)

        initViews()
        initObservables()
        initScrollListener()
    }

    private fun initViews() {
        adapter = RepositoriesAdapter(::onClickRepository)
        recyclerView?.adapter = adapter
        viewModel.loadRepositories()

        retryButton?.setOnClickListener { viewModel.loadRepositories(isRefresh = true) }
    }

    private fun initObservables() {
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is RepositoriesState.Loading -> {
                    isLoading = true

                    if (adapter.itemCount > 0)
                        swipeRefreshLayout?.isRefreshing = true
                    else
                        progressBar?.visibility = View.VISIBLE

                    errorView?.visibility = View.GONE
                }
                is RepositoriesState.LoadingNextPage -> {
                    isLoading = true
                    progressBarPagination?.visibility = View.VISIBLE
                    progressBar?.visibility = View.GONE
                }
                is RepositoriesState.Success -> {
                    isLoading = false
                    swipeRefreshLayout?.isRefreshing = false
                    progressBar?.visibility = View.GONE
                    progressBarPagination?.visibility = View.GONE
                    errorView?.visibility = View.GONE

                    recyclerView?.visibility = View.VISIBLE

                    if (state.isCache)
                        Snackbar.make(
                            swipeRefreshLayout,
                            getString(R.string.data_from_cache),
                            Snackbar.LENGTH_LONG
                        ).show()

                    if (adapter.itemCount > 0 && !state.isRefresh)
                        adapter.add(state.repositories)
                    else
                        adapter.update(state.repositories)
                }
                is RepositoriesState.Error -> {
                    isLoading = false
                    swipeRefreshLayout?.isRefreshing = false
                    errorView?.visibility = View.VISIBLE
                    recyclerView?.visibility = View.GONE

                    errorText?.text = state.message ?: getString(R.string.unknown_error)
                }
            }
        })
    }

    private fun initScrollListener() {
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerView.layoutManager?.let { layoutManager ->
                    val visibleItemCount: Int = layoutManager.childCount
                    val totalItemCount: Int = layoutManager.itemCount
                    val firstVisibleItemPosition: Int =
                        (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (!isLoading) {
                        if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                            && firstVisibleItemPosition >= 0
                        ) {
                            if (!viewModel.isCache)
                                viewModel.loadRepositories()
                        }
                    }
                }
            }
        })
    }

    private fun onClickRepository(ownerLogin: String) {
        val action =
            RepositoriesFragmentDirections.actionRepositoriesFragmentToRepositoryDetailsFragment(
                ownerLogin
            )
        navController.navigate(action)
    }

    override fun onRefresh() {
        viewModel.loadRepositories(isRefresh = true)
    }
}