package com.ranggacikal.assesmentgithubuser.feature.user.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ranggacikal.assesmentgithubuser.feature.user.data.local.User
import com.ranggacikal.feature.user.R
import com.ranggacikal.feature.user.databinding.ItemSavedUserBinding

class SavedUserAdapter(
    private val users: MutableList<User>,
    private val onDeleteClick: ((Int, Int) -> Unit)? = null
) : RecyclerView.Adapter<SavedUserAdapter.SavedUserViewHolder>() {

    inner class SavedUserViewHolder(val binding: ItemSavedUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, position: Int) {
            with(binding) {
                tvNameSavedUser.text = user.login
                tvUsernameSavedUser.text = user.name
                tvEmailSavedUser.text = user.email
                tvBioSavedUser.text = user.bio

                Glide.with(root.context)
                    .load(user.avatarUrl)
                    .error(R.drawable.ic_failed_load_image)
                    .circleCrop()
                    .into(ivItemSavedUser)

                btnDeleteSavedUser.setOnClickListener {
                    onDeleteClick?.invoke(position, user.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedUserViewHolder {
        val binding = ItemSavedUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SavedUserViewHolder, position: Int) {
        holder.bind(users[position], position)
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    fun removeUserAt(position: Int) {
        if (position in users.indices) {
            users.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}