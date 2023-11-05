package com.group.blogapp.article.dto.request

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

data class ArticleCreateRequest(
    @ApiModelProperty(example = "title")
    @field:NotBlank(message = "Title is required.")
    val title: String,
    @ApiModelProperty(example = "content")
    @field:NotBlank(message = "Content is required.")
    val content: String
) {
}