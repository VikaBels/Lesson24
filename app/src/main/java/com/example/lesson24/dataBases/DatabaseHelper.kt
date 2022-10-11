package com.example.lesson24.dataBases

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.lesson24.utils.listComment
import com.example.lesson24.utils.listPost
import com.example.lesson24.utils.listUser

class DatabaseHelper(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        private const val DATABASE_NAME = "Lesson_24.db"
        private const val VERSION = 1
        private const val TABLE_NAME_POST = "post"
        private const val TABLE_NAME_USER = "user"
        private const val TABLE_NAME_COMMENT = "comment"
    }

    override fun onCreate(db: SQLiteDatabase) {
        createTableUser(db)
        createTablePost(db)
        createTableComment(db)
    }

    private fun createTablePost(db: SQLiteDatabase) {
        db.compileStatement(
            "CREATE TABLE post " +
                    "(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "user_id INTEGER NOT NULL," +
                    "title TEXT NOT NULL," +
                    "body TEXT NOT NULL," +
                    "rate INTEGER NOT NULL );"
        ).execute()

        listPost().forEach {
            db.compileStatement(
                "INSERT INTO post (user_id, title, body, rate) VALUES(?,?,?,?);"
            ).apply {
                bindLong(1, it.userId)
                bindString(2, it.title)
                bindString(3, it.body)
                bindLong(4, it.rate)
            }.executeInsert()
        }
    }

    private fun createTableUser(db: SQLiteDatabase) {
        db.compileStatement(
            "CREATE TABLE user" +
                    "(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "first_name TEXT NOT NULL, " +
                    "last_name TEXT NOT NULL," +
                    "email TEXT NOT NULL );"
        ).execute()

        listUser().forEach {
            db.compileStatement(
                "INSERT INTO user (first_name, last_name, email) VALUES(?,?,?);"
            ).apply {
                bindString(1, it.firstName)
                bindString(2, it.lastName)
                bindString(3, it.email)
            }.executeInsert()
        }
    }

    private fun createTableComment(db: SQLiteDatabase) {
        db.compileStatement(
            "CREATE TABLE comment " +
                    "(_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    " post_id INTEGER NOT NULL," +
                    " user_id INTEGER NOT NULL," +
                    " text TEXT NOT NULL );"
        ).execute()

        listComment().forEach {
            db.compileStatement(
                "INSERT INTO comment (post_id, user_id, text) VALUES(?,?,?);"
            ).apply {
                bindLong(1, it.postId)
                bindLong(2, it.userId)
                bindString(3, it.text)
            }.executeInsert()
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.apply {
            compileStatement("DROP TABLE IF EXISTS $TABLE_NAME_POST")
            compileStatement("DROP TABLE IF EXISTS $TABLE_NAME_USER")
            compileStatement("DROP TABLE IF EXISTS $TABLE_NAME_COMMENT")
        }
        onCreate(db)
    }
}