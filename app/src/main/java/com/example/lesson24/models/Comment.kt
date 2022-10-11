package com.example.lesson24.models

class Comment @JvmOverloads constructor(
    email: String? = null,
    text: String? = null,
) {
    var safeEmail: String? = email
    var safeText: String? = text
}