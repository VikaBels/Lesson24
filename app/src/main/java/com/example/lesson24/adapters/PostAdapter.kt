package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.databinding.ItemBinding
import com.example.lesson24.models.Post
import com.example.lesson24.listeners.PostListener

class PostAdapter(
    private val postListener: PostListener,
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    private var postList = ArrayList<Post>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, postListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem = postList[position]
        holder.bind(postItem)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setListItems(listPost: ArrayList<Post>) {
        postList = listPost
        notifyDataSetChanged()
    }

    class PostViewHolder(
        private val binding: ItemBinding,
        private val listener: PostListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(postItem: Post) {
            binding.title.text = postItem.safeTitle
            binding.email.text = postItem.safeEmail
            binding.body.text = postItem.safeBody

            binding.oneItem.setOnClickListener {
                listener.startPostDetailActivity(postItem)
            }
        }
    }
}