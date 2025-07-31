package com.ranggacikal.assesmentgithubuser.feature.user.ui.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import com.bumptech.glide.Glide
import com.ranggacikal.assesmentgithubuser.api.data.UserDetail
import com.ranggacikal.assesmentgithubuser.feature.user.utils.Constant.COMMON_HANDLING_EMPTY
import com.ranggacikal.feature.user.R
import com.ranggacikal.feature.user.databinding.DialogDetailUserBinding

class DetailUserDialog(private val context: Context) {
    private var dialog: Dialog? = null
    private lateinit var binding: DialogDetailUserBinding

    fun showDialogDetailUser(
        userDetail: UserDetail? = null,
        onSave: (() -> Unit)? = null
    ) {
        dialog = Dialog(context)
        binding = DialogDetailUserBinding.inflate(LayoutInflater.from(context))

        dialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(binding.root)
            setCancelable(false)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            Glide.with(context)
                .load(userDetail?.avatarUrl)
                .error(R.drawable.ic_failed_load_image)
                .circleCrop()
                .into(binding.ivUserDetail)
            binding.tvLoginNameUserDetail.text = userDetail?.login
            binding.tvNameUserDetail.text = userDetail?.name
            binding.tvEmailUserDetail.text =
                userDetail?.email.orEmpty().ifEmpty { COMMON_HANDLING_EMPTY }
            binding.tvBioUserDetail.text =
                userDetail?.bio.orEmpty().ifEmpty { COMMON_HANDLING_EMPTY }
            binding.btnCloseDialogDetailUser.setOnClickListener {
                dialog?.dismiss()
            }
            binding.btnSaveDialogDetailUser.setOnClickListener {
                onSave?.invoke()
            }
            show()
        }
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}