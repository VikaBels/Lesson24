package com.example.lesson24.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson24.repositories.CursorRepository
import com.example.lesson24.activities.DetailPostActivity.Companion.KEY_SEND_ID_POST
import com.example.lesson24.adapters.CommentAdapter
import com.example.lesson24.databinding.ActivityListCommentBinding

class ListCommentActivity : AppCompatActivity() {
    private var bindingListComment: ActivityListCommentBinding? = null
    private val elementAdapter = CommentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingListComment = ActivityListCommentBinding.inflate(layoutInflater)
        setContentView(bindingListComment.root)

        this.bindingListComment = bindingListComment

        setUpAdapter()

        fillingListComment()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingListComment = null
    }

    private fun fillingListComment() {
        val listComment = CursorRepository().getAllComment(getIdCurrentPost())

        if (listComment.isEmpty()) {
            changeVisibility()
        } else {
            elementAdapter.setListComment(listComment)
        }
    }

    private fun getIdCurrentPost(): Long? {
        return intent.extras?.getLong(KEY_SEND_ID_POST)
    }

    private fun changeVisibility() {
        bindingListComment?.textNoComment?.isVisible = true
        bindingListComment?.listComment?.isVisible = false
    }

    private fun setUpAdapter() {
        bindingListComment?.listComment?.apply {
            adapter = elementAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}