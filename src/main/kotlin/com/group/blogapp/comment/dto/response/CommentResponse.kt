package com.group.blogapp.comment.dto.response

import com.group.blogapp.comment.domain.Comment

data class CommentResponse(
    val commentId: Long,
    val email: String,
    val content: String
) {
    companion object {
        fun of(comment: Comment): CommentResponse {
            return CommentResponse(
                commentId = comment.id!!,
                email = comment.user!!.email,
                content = comment.content
            )
        }
    }
}