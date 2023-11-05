package com.group.blogapp.article.dto.response

import com.group.blogapp.article.domain.Article
import io.swagger.annotations.ApiModelProperty

data class ArticleResponse(
    @ApiModelProperty(example = "1")
    val articleId: Long,
    @ApiModelProperty(example = "email@urssu.com")
    val email: String,
    @ApiModelProperty(example = "title")
    val title: String,
    @ApiModelProperty(example = "content")
    val content: String
) {
    companion object {
        fun of(article: Article): ArticleResponse {
            return ArticleResponse(
                articleId = article.id!!,
                email = article.user!!.email,
                title = article.title,
                content = article.content
            )
        }
    }
}