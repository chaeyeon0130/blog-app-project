package com.group.blogapp.article.dto.request

import javax.validation.constraints.NotBlank

data class ArticleUpdateRequest(
    @field:NotBlank(message = "Title is required.")
    val title: String,

    @field:NotBlank(message = "Content is required.")
    val content: String
) {
}