package com.ranggacikal.assesmentgithubuser.feature.user.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranggacikal.assesmentgithubuser.feature.user.base.BaseFragment
import com.ranggacikal.assesmentgithubuser.feature.user.ui.adapter.SavedUserAdapter
import com.ranggacikal.assesmentgithubuser.feature.user.viewmodel.SavedUserViewModel
import com.ranggacikal.feature.user.R
import com.ranggacikal.feature.user.databinding.FragmentSavedUserBinding
import com.ranggacikal.feature.user.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedUserFragment : BaseFragment<FragmentSavedUserBinding>() {

    private lateinit var savedUserAdapter: SavedUserAdapter
    private val viewModel: SavedUserViewModel by viewModels()

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSavedUserBinding {
        return FragmentSavedUserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSavedUser()
        setupContent()
        populateSavedUserData()
    }

    private fun observeSavedUser() = with(viewModel) {
        observeDataFlow(
            savedUser,
            onLoad = {
                showProgressDialog(true)
            },
            onError = {
                showProgressDialog(false)
                showErrorDialog(it){}
            }
        ) {
            showProgressDialog(false)
            users = it
            savedUserAdapter.setUsers(users)
        }
    }

    private fun populateSavedUserData() = with(viewModel) {
        loadUsers()
    }

    private fun setupContent() = with(binding) {
        savedUserAdapter = SavedUserAdapter(mutableListOf()) { position, userId ->
            savedUserAdapter.removeUserAt(position)
            viewModel.deleteUserById(userId)
            populateSavedUserData()
        }
        rvSavedUser.apply {
            adapter = savedUserAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}