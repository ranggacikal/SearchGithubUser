package com.ranggacikal.assesmentgithubuser.feature.user.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.ranggacikal.feature.user.databinding.ItemDialogLoadingBinding

class LoadingDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private lateinit var binding: ItemDialogLoadingBinding

    fun show() {
        dialog = Dialog(context)
        binding = ItemDialogLoadingBinding.inflate(LayoutInflater.from(context))

        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            show()
        }
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}