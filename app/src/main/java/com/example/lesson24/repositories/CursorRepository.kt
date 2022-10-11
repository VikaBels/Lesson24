package com.example.lesson24.repositories

import com.example.lesson24.App.Companion.getDb
import com.example.lesson24.models.Comment
import com.example.lesson24.models.Post
import java.util.*

class CursorRepository {
    companion object {
        private const val POST_ID = "post_id"
        private const val TITLE = "title"
        private const val USER_EMAIL = "user_email"
        private const val BODY = "body"
        private const val FULL_NAME = "fullName"

        private const val EMAIL_COMMENTATOR = "email_commentator"
        private const val TEXT_COMMENT = "text_comment"
    }

    fun getAllPosts(): ArrayList<Post> {
        val listPost = ArrayList<Post>()

        val cursor = getDb().rawQuery(
            "SELECT p._id as $POST_ID, p.title as $TITLE, p.body as $BODY, " +
                    "u.email as $USER_EMAIL, u.first_name || \" \" || u.last_name as $FULL_NAME FROM user u " +
                    "JOIN post p ON u._id = p.user_id",
            null
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val idPostCursor = cursor.getColumnIndexOrThrow(POST_ID)

                val titleCursor = cursor.getColumnIndexOrThrow(TITLE)
                val userIdCursor = cursor.getColumnIndexOrThrow(USER_EMAIL)
                val bodyCursor = cursor.getColumnIndexOrThrow(BODY)
                val fullNameCursor = cursor.getColumnIndexOrThrow(FULL_NAME)

                do {
                    val post = Post(
                        cursor.getLong(idPostCursor),
                        cursor.getString(titleCursor),
                        cursor.getString(userIdCursor),
                        cursor.getString(bodyCursor),
                        cursor.getString(fullNameCursor),
                    )

                    listPost.add(post)

                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return listPost
    }

    fun getAllComment(idCurrentPost: Long?): ArrayList<Comment> {
        val listComment = ArrayList<Comment>()

        val cursor = getDb().rawQuery(
            "SELECT u.email as $EMAIL_COMMENTATOR," +
                    "c.text as $TEXT_COMMENT FROM comment c " +
                    "JOIN post p ON c.post_id = p._id " +
                    "JOIN user u ON u._id = c.user_id " +
                    "WHERE p._id = ?",
            arrayOf(
                idCurrentPost?.toString()
            )
        )

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val emailCommentatorCursor = cursor.getColumnIndexOrThrow(EMAIL_COMMENTATOR)
                val textCommentCursor = cursor.getColumnIndexOrThrow(TEXT_COMMENT)

                do {
                    val comment = Comment(
                        cursor.getString(emailCommentatorCursor),
                        cursor.getString(textCommentCursor),
                    )

                    listComment.add(comment)

                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return listComment
    }
}