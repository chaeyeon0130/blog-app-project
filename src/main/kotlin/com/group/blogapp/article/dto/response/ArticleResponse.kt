package com.group.blogapp.article.dto.response

import com.group.blogapp.article.domain.Article

data class ArticleResponse(
    val articleId: Long,
    val email: String,
    val title: String,
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