package com.ranggacikal.assesmentgithubuser.feature.user.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.ranggacikal.assesmentgithubuser.feature.user.ui.dialog.ErrorDialog
import com.ranggacikal.assesmentgithubuser.feature.user.ui.dialog.LoadingDialog
import com.ranggacikal.assesmentgithubuser.feature.user.utils.ResultState

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var errorDialog: ErrorDialog

    abstract fun bindingInflater(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container)
        loadingDialog = LoadingDialog(requireContext())
        errorDialog = ErrorDialog(requireContext())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        loadingDialog.dismiss()
    }

    fun showProgressDialog(isShow: Boolean) {
        if (isShow) loadingDialog.show()
        else loadingDialog.dismiss()
    }

    fun showErrorDialog(message: String, onClose: (() -> Unit)? = null) {
        errorDialog.show(message, onClose)
    }

    protected fun <T> observeDataFlow(
        liveData: LiveData<ResultState<T>>,
        onLoad: (() -> Unit)? = null,
        onError: ((String) -> Unit)? = null,
        onSuccess: (T) -> Unit
    ) {
        liveData.observe(viewLifecycleOwner) { dataFlow ->
            when (dataFlow) {
                is ResultState.Loading -> {
                    onLoad?.invoke()
                }
                is ResultState.Error -> {
                    onError?.invoke(dataFlow.message)
                }
                is ResultState.Success -> {
                    onSuccess(dataFlow.data)
                }
            }
        }
    }

    fun View.show(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

}