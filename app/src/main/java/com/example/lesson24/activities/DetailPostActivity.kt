package com.example.lesson24.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson24.activities.MainActivity.Companion.KEY_SEND_POST
import com.example.lesson24.databinding.ActivityDetailPostBinding
import com.example.lesson24.models.Post

class DetailPostActivity : AppCompatActivity() {
    companion object {
        const val KEY_SEND_ID_POST = "KEY_SEND_ID_POST"
    }

    private var bindingDetailPost: ActivityDetailPostBinding? = null
    private var itemPost: Post? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingDetailPost = ActivityDetailPostBinding.inflate(layoutInflater)
        setContentView(bindingDetailPost.root)

        this.bindingDetailPost = bindingDetailPost

        readObjectPost()

        setupListeners(bindingDetailPost)

        setDetailPost(bindingDetailPost)
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingDetailPost = null
    }

    private fun readObjectPost() {
        itemPost = intent.extras?.getParcelable(KEY_SEND_POST)
    }

    private fun setupListeners(bindingDetailPost: ActivityDetailPostBinding) {
        bindingDetailPost.buttonComments.setOnClickListener {
            startListCommentActivity()
        }
    }

    private fun startListCommentActivity() {
        val intent = Intent(this, ListCommentActivity::class.java)
        intent.putExtra(KEY_SEND_ID_POST, itemPost?.safeId)
        startActivity(intent)
    }

    private fun setDetailPost(bindingDetailPost: ActivityDetailPostBinding) {
        bindingDetailPost.name.text = itemPost?.safeFullName
        bindingDetailPost.email.text = itemPost?.safeEmail
        bindingDetailPost.title.text = itemPost?.safeTitle
        bindingDetailPost.body.text = itemPost?.safeBody
    }
}