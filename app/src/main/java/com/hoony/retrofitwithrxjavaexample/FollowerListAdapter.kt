package com.hoony.retrofitwithrxjavaexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hoony.retrofitwithrxjavaexample.databinding.ItemFollowerBinding
import com.hoony.retrofitwithrxjavaexample.retrofit.GithubUser

class FollowerListAdapter(private val githubFollowerList: List<GithubUser>): ListAdapter<GithubUser, FollowerListAdapter.FollowerViewHolder>(
    object : DiffUtil.ItemCallback<GithubUser>() {
        override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean =
            oldItem.hashCode() == newItem.hashCode()
    }
) {
    override fun getItemCount(): Int = githubFollowerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        return FollowerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_follower,
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.bind(githubFollowerList[position])
    }

    inner class FollowerViewHolder(private val binding: ItemFollowerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(githubUser: GithubUser) {
            binding.gitHubUser = githubUser

            Glide.with(binding.root)
                .load(githubUser.avatar_url)
                .into(binding.image)
        }
    }
}