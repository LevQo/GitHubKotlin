package com.levqo.githubkotlin.presentation.repository_details.view

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.levqo.githubkotlin.R

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }

}