package com.group.blogapp.article.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class ArticleUpdateRequest(
    @field:NotBlank(message = "Email is required.")
    @field:Email(message = "Invalid email format.")
    val email: String,

    @field:NotBlank(message = "Password is required.")
    val password: String,

    @field:NotBlank(message = "Title is required.")
    val title: String,

    @field:NotBlank(message = "Content is required.")
    val content: String
) {
}