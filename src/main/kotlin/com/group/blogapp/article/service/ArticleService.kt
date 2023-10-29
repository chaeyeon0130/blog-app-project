package com.group.blogapp.article.service

import com.group.blogapp.article.domain.Article
import com.group.blogapp.article.dto.request.ArticleCreateRequest
import com.group.blogapp.article.dto.request.ArticleUpdateRequest
import com.group.blogapp.article.dto.response.ArticleResponse
import com.group.blogapp.auth.dto.AuthInfo

interface ArticleService {
    fun saveArticle(authInfo: AuthInfo, request: ArticleCreateRequest): ArticleResponse
    fun updateArticle(id: Long, request: ArticleUpdateRequest): ArticleResponse
    fun deleteArticle(id: Long)
    fun findArticle(id: Long): Article
}