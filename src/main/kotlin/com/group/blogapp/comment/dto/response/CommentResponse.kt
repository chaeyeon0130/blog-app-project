package com.group.blogapp.comment.dto.response

import com.group.blogapp.comment.domain.Comment
import io.swagger.annotations.ApiModelProperty

data class CommentResponse(
    @ApiModelProperty(example = "1")
    val commentId: Long,
    @ApiModelProperty(example = "email@urssu.com")
    val email: String,
    @ApiModelProperty(example = "content")
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