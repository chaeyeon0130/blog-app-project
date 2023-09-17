package com.group.blogapp.article.service

import com.group.blogapp.article.domain.Article
import com.group.blogapp.article.dto.request.ArticleCreateRequest
import com.group.blogapp.article.dto.request.ArticleDeleteRequest
import com.group.blogapp.article.dto.request.ArticleUpdateRequest
import com.group.blogapp.article.dto.response.ArticleResponse

interface ArticleService {
    fun saveArticle(request: ArticleCreateRequest): ArticleResponse
    fun updateArticle(id: Long, request: ArticleUpdateRequest): ArticleResponse
    fun deleteArticle(id: Long, request: ArticleDeleteRequest)
    fun findArticle(id: Long): Article?
}