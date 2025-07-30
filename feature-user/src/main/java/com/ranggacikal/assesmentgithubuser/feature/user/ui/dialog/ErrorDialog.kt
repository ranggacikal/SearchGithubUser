package com.ranggacikal.assesmentgithubuser.feature.user.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.ranggacikal.feature.user.databinding.ItemDialogErrorBinding

class ErrorDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private lateinit var binding: ItemDialogErrorBinding

    fun show(errorMessage: String, onClose: (() -> Unit)? = null) {
        dialog = Dialog(context)
        binding = ItemDialogErrorBinding.inflate(LayoutInflater.from(context))

        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(true)
            window?.setBackgroundDrawableResource(android.R.color.transparent)

            binding.tvDialogErrorMessage.text = errorMessage
            binding.btnClose.setOnClickListener {
                dismiss()
                onClose?.invoke()
            }

            show()
        }
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}