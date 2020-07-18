package com.levqo.githubkotlin.presentation.repositories.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.levqo.githubkotlin.R
import com.levqo.githubkotlin.presentation.repositories.view.adapter.RepositoriesAdapter
import com.levqo.githubkotlin.presentation.repositories.view.adapter.RepositoryItemView
import kotlinx.android.synthetic.main.fragment_repositories.*

class RepositoriesFragment : Fragment(R.layout.fragment_repositories),
    SwipeRefreshLayout.OnRefreshListener {

    private val isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout?.setOnRefreshListener(this)

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
                            (recyclerView.adapter as RepositoriesAdapter).add(
                                listOf(
                                    RepositoryItemView(
                                        name = "Name",
                                        description = "Description"
                                    ),
                                    RepositoryItemView(
                                        name = "Name",
                                        description = "Description"
                                    ),
                                    RepositoryItemView(
                                        name = "Name",
                                        description = "Description"
                                    ),
                                    RepositoryItemView(
                                        name = "Name",
                                        description = "Description"
                                    ),
                                    RepositoryItemView(
                                        name = "Name",
                                        description = "Description"
                                    ),
                                    RepositoryItemView(
                                        name = "Name",
                                        description = "Description"
                                    )
                                )
                            )
                        }
                    }
                }


            }
        })

        recyclerView?.adapter = RepositoriesAdapter().also {
            it.update(
                listOf(
                    RepositoryItemView(
                        name = "Name",
                        description = "Description"
                    ),
                    RepositoryItemView(
                        name = "Name",
                        description = "Description"
                    ),
                    RepositoryItemView(
                        name = "Name",
                        description = "Description"
                    ),
                    RepositoryItemView(
                        name = "Name",
                        description = "Description"
                    ),
                    RepositoryItemView(
                        name = "Name",
                        description = "Description"
                    ),
                    RepositoryItemView(
                        name = "Name",
                        description = "Description"
                    )
                )
            )
        }
    }

    override fun onRefresh() {

    }
}