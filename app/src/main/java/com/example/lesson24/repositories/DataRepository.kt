package com.example.lesson24.repositories

import android.database.sqlite.SQLiteDatabase
import com.example.lesson24.models.CommentInfo
import com.example.lesson24.models.PostInfo
import java.util.*

class DataRepository(private val db: SQLiteDatabase) {
    companion object {
        private const val POST_ID = "post_id"
        private const val TITLE = "title"
        private const val USER_EMAIL = "user_email"
        private const val BODY = "body"
        private const val FULL_NAME = "fullName"

        private const val EMAIL_COMMENTATOR = "email_commentator"
        private const val TEXT_COMMENT = "text_comment"
    }

    fun getAllPosts(): ArrayList<PostInfo> {
        val listPost = ArrayList<PostInfo>()

        val cursor = db.rawQuery(
            "SELECT p._id as $POST_ID, p.title as $TITLE, p.body as $BODY, " +
                    "u.email as $USER_EMAIL, u.first_name || \" \" || u.last_name as $FULL_NAME FROM user u " +
                    "JOIN post p ON u._id = p.user_id",
            null
        )

        cursor?.use {
            if (cursor.moveToFirst()) {
                val idPostCursor = cursor.getColumnIndexOrThrow(POST_ID)

                val titleCursor = cursor.getColumnIndexOrThrow(TITLE)
                val userIdCursor = cursor.getColumnIndexOrThrow(USER_EMAIL)
                val bodyCursor = cursor.getColumnIndexOrThrow(BODY)
                val fullNameCursor = cursor.getColumnIndexOrThrow(FULL_NAME)

                do {
                    val post = PostInfo(
                        cursor.getLong(idPostCursor),
                        cursor.getString(titleCursor),
                        cursor.getString(userIdCursor),
                        cursor.getString(bodyCursor),
                        cursor.getString(fullNameCursor),
                    )

                    listPost.add(post)

                } while (cursor.moveToNext())
            }
        }
        return listPost
    }

    fun getAllComment(idCurrentPost: Long?): ArrayList<CommentInfo> {
        val listComment = ArrayList<CommentInfo>()

        val cursor = db.rawQuery(
            "SELECT u.email as $EMAIL_COMMENTATOR," +
                    "c.text as $TEXT_COMMENT FROM comment c " +
                    "JOIN post p ON c.post_id = p._id " +
                    "JOIN user u ON u._id = c.user_id " +
                    "WHERE p._id = ?",
            arrayOf(
                idCurrentPost?.toString()
            )
        )

        cursor?.use {
            if (cursor.moveToFirst()) {
                val emailCommentatorCursor = cursor.getColumnIndexOrThrow(EMAIL_COMMENTATOR)
                val textCommentCursor = cursor.getColumnIndexOrThrow(TEXT_COMMENT)

                do {
                    val comment = CommentInfo(
                        cursor.getString(emailCommentatorCursor),
                        cursor.getString(textCommentCursor),
                    )

                    listComment.add(comment)

                } while (cursor.moveToNext())
            }
        }
        return listComment
    }
}