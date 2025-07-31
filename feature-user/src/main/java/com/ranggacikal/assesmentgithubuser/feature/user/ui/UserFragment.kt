package com.ranggacikal.assesmentgithubuser.feature.user.ui

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.ranggacikal.assesmentgithubuser.feature.user.base.BaseFragment
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.User
import com.ranggacikal.assesmentgithubuser.feature.user.ui.adapter.UserAdapter
import com.ranggacikal.assesmentgithubuser.feature.user.ui.dialog.DetailUserDialog
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.COMMON_HANDLING_EMPTY
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.DELAY_SEARCH
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.LENGTH_SEARCH
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.NAVIGATE_TO_SAVED_USER
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.ONE_STRING
import com.ranggacikal.assesmentgithubuser.feature.user.utils.orDefault
import com.ranggacikal.assesmentgithubuser.feature.user.viewmodel.SearchUserViewModel
import com.ranggacikal.feature.user.R
import com.ranggacikal.feature.user.databinding.FragmentUserBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : BaseFragment<FragmentUserBinding>() {

    private var searchJob: Job? = null
    private val viewModel: SearchUserViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter
    private lateinit var userDetailDialog: DetailUserDialog

    override fun bindingInflater(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeSearchUser()
        observeDetailUser()
        observeAdduserLocal()
        setupContent()
        setupListener()
        userDetailDialog = DetailUserDialog(requireContext())
    }

    private fun setupContent() = with(binding) {
        binding.btnSavedUser.show(true)
        binding.rvListUser.show(false)
        userAdapter = UserAdapter(mutableListOf()) { userId ->
            if (!viewModel.isLoading) {
                viewModel.getDetailUser(userId)
            }
        }
        rvListUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
        btnSavedUser.setOnClickListener {
            val uri = Uri.parse(NAVIGATE_TO_SAVED_USER)
            findNavController().navigate(uri)
        }
    }

    private fun observeSearchUser() = with(viewModel) {
        observeDataFlow(
            searchUser,
            onLoad = {
                showProgressDialog(true)
            },
            onError = {
                showProgressDialog(false)
                showErrorDialog(it){}
            }
        ) {
            showProgressDialog(false)
            binding.rvListUser.show(true)
            binding.btnSavedUser.show(false)
            userAdapter.addUsers(it.items, isFromEditText)
        }
    }

    private fun observeDetailUser() = with(viewModel) {
        observeDataFlow(
            detailUser,
            onLoad = {
                showProgressDialog(true)
            },
            onError = {
                showProgressDialog(false)
                showErrorDialog(it){}
            }
        ) {
            showProgressDialog(false)
            userDetailDialog.showDialogDetailUser(it){
                addUser(
                    user = User(
                        login = it.login.orDefault(COMMON_HANDLING_EMPTY),
                        id = it.id,
                        avatarUrl = it.avatarUrl.orDefault(COMMON_HANDLING_EMPTY),
                        name = it.name.orDefault(COMMON_HANDLING_EMPTY),
                        email = it.email.orDefault(COMMON_HANDLING_EMPTY),
                        bio = it.bio.orDefault(COMMON_HANDLING_EMPTY)
                    )
                )
            }
        }
    }

    private fun observeAdduserLocal() = with(viewModel) {
        observeDataFlow(
            addUserLocal,
            onLoad = {
                showProgressDialog(true)
            },
            onError = {
                showProgressDialog(false)
                showErrorDialog(it){}
            }
        ) {
            showProgressDialog(false)
            userDetailDialog.dismiss()
        }
    }

    private fun setupListener() = with(binding) {
        edtSearchUser.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isEmpty() == true) {
                    binding.rvListUser.show(false)
                    binding.btnSavedUser.show(true)
                } else {
                    binding.rvListUser.show(true)
                    binding.btnSavedUser.show(false)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.query = s.toString()
                if (viewModel.query.length >= LENGTH_SEARCH) {
                    viewModel.isFromEditText = true
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        delay(500)
                        searchUser(viewModel.query, ONE_STRING)
                    }
                }
            }

        })

        rvListUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    viewModel.currentPage++
                    viewModel.isFromEditText = false
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        delay(500)
                        searchUser(viewModel.query, viewModel.currentPage.toString())
                    }
                }
            }
        })
    }

    private fun searchUser(query: String, page: String) = with(viewModel) {
        getSearchUser(query, page)
    }

    override fun onResume() {
        super.onResume()
        setupContent()
    }
}