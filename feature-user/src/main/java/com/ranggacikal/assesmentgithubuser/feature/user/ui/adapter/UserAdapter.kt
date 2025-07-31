package com.ranggacikal.assesmentgithubuser.feature.user.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ranggacikal.assesmentgithubuser.api.data.UserData
import com.ranggacikal.feature.user.R
import com.ranggacikal.feature.user.databinding.ItemUserBinding

class UserAdapter(
    private val users: MutableList<UserData>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            tvItemUserName.text = user.login.orEmpty()
            Glide.with(root.context)
                .load(user.avatarUrl)
                .error(R.drawable.ic_failed_load_image)
                .into(ivItemUser)
            root.setOnClickListener {
                onItemClick(user.id)
            }
        }
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(newUser: List<UserData>, isFromEditText: Boolean) {
        if (isFromEditText) {
            users.clear()
        }
        users.addAll(newUser)
        notifyDataSetChanged()
    }
}