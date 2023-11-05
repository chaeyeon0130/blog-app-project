package com.group.blogapp.comment.dto.request

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

data class CommentUpdateRequest(
    @ApiModelProperty(example = "content")
    @field:NotBlank(message = "Content is required.")
    val content: String
) {
}