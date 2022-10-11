package com.example.lesson24.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson24.repositories.CursorRepository
import com.example.lesson24.adapters.PostAdapter
import com.example.lesson24.databinding.ActivityMainBinding
import com.example.lesson24.listeners.PostListener
import com.example.lesson24.models.Post

class MainActivity : AppCompatActivity(), PostListener {
    companion object {
        const val KEY_SEND_POST = "KEY_SEND_POST"
    }

    private var bindingMain: ActivityMainBinding? = null
    private var postAdapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bindingMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingMain.root)

        this.bindingMain = bindingMain
        postAdapter = PostAdapter(this)

        setUpAdapter()

        fillingListPost()
    }

    override fun onDestroy() {
        super.onDestroy()
        bindingMain = null
        postAdapter = null
    }

    override fun startPostDetailActivity(postItem: Post) {
        val intent = Intent(this, DetailPostActivity::class.java)
        intent.putExtra(KEY_SEND_POST, postItem)
        startActivity(intent)
    }

    private fun fillingListPost() {
        val listPost = CursorRepository().getAllPosts()

        if (listPost.isEmpty()) {
            changeVisibility()
        } else {
            postAdapter?.setListItems(listPost)
        }
    }

    private fun changeVisibility() {
        bindingMain?.textNoComment?.isVisible = true
        bindingMain?.listPost?.isVisible = false
    }

    private fun setUpAdapter() {
        bindingMain?.listPost?.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}