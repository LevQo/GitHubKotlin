package com.levqo.githubkotlin.presentation.repositories.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.levqo.githubkotlin.R
import com.levqo.githubkotlin.data.models.GitHubRepositoryModel
import kotlinx.android.synthetic.main.item_repository.view.*

class RepositoriesAdapter(private val onClick: (ownerLogin: String) -> Unit) :
    RecyclerView.Adapter<RepositoriesAdapter.RepositoryHolder>() {

    private val items: List<GitHubRepositoryModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryHolder {
        return RepositoryHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repository,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepositoryHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RepositoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: GitHubRepositoryModel) {
            with(itemView) {
                name?.text = item.name
                description?.text = item.description ?: "-"
//                setOnClickListener { onClick(item.owner.login) } // TODO: implement click
            }
        }
    }

    fun update(list: List<GitHubRepositoryModel>) {
        (items as ArrayList).also { it.clear() }.addAll(list)
        notifyDataSetChanged()
    }

    fun add(list: List<GitHubRepositoryModel>) {
        val oldListSize = itemCount
        (items as ArrayList).addAll(list)
        notifyItemRangeInserted(oldListSize, list.size)
    }
}

