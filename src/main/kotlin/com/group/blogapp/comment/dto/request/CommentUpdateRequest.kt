package com.group.blogapp.comment.dto.request

import javax.validation.constraints.NotBlank

data class CommentUpdateRequest(
    @field:NotBlank(message = "Content is required.")
    val content: String
) {
}