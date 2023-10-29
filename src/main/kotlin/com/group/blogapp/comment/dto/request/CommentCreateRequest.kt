package com.group.blogapp.comment.dto.request

import javax.validation.constraints.NotBlank

data class CommentCreateRequest(
    @field:NotBlank(message = "Content is required.")
    val content: String
) {
}