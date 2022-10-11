package com.example.lesson24.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson24.databinding.ItemCommentBinding
import com.example.lesson24.models.CommentInfo

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    private var commentList = ArrayList<CommentInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val commentItem = commentList[position]
        holder.bind(commentItem)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    fun setListComment(listComment: ArrayList<CommentInfo>) {
        commentList = listComment
        notifyDataSetChanged()
    }

    class CommentViewHolder(
        private val binding: ItemCommentBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(commentItem: CommentInfo) {
            binding.email.text = commentItem.email
            binding.text.text = commentItem.text
        }
    }
}