package com.group.blogapp.article.service

import com.group.blogapp.article.domain.Article
import com.group.blogapp.article.domain.ArticleRepository
import com.group.blogapp.article.dto.request.ArticleCreateRequest
import com.group.blogapp.article.dto.request.ArticleUpdateRequest
import com.group.blogapp.article.dto.response.ArticleResponse
import com.group.blogapp.article.exception.ArticleNotFoundException
import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.user.service.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ArticleServiceImpl(
    private val articleRepository: ArticleRepository,
    private val userService: UserService
) : ArticleService{
    @Transactional
    override fun saveArticle(authInfo: AuthInfo, request: ArticleCreateRequest): ArticleResponse {
        val foundUser = userService.findUser(authInfo.email)

        val newArticle = Article(request.title, request.content)
        newArticle.user = foundUser

        val savedArticle = articleRepository.save(newArticle)

        return ArticleResponse.of(savedArticle)
    }

    @Transactional
    override fun updateArticle(id: Long, request: ArticleUpdateRequest): ArticleResponse {
        val foundArticle = findArticle(id)

        foundArticle.title = request.title
        foundArticle.content = request.content

        return ArticleResponse.of(foundArticle)
    }

    @Transactional
    override fun deleteArticle(id: Long) {
        val foundArticle = findArticle(id)

        articleRepository.delete(foundArticle)
    }

    @Transactional(readOnly = true)
    override fun findArticle(id: Long): Article {
        return articleRepository.findByIdOrNull(id) ?: throw ArticleNotFoundException("Article is not found.")
    }
}