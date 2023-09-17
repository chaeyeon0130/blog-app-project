package com.group.blogapp.article.controller

import com.group.blogapp.article.dto.request.ArticleCreateRequest
import com.group.blogapp.article.dto.request.ArticleDeleteRequest
import com.group.blogapp.article.dto.request.ArticleUpdateRequest
import com.group.blogapp.article.dto.response.ArticleResponse
import com.group.blogapp.article.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping
class ArticleController(
    private val articleService: ArticleService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    fun saveArticle(@Valid @RequestBody request: ArticleCreateRequest): ArticleResponse {
        return articleService.saveArticle(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/articles/{id}")
    fun updateArticle(@PathVariable("id") id: Long,
                      @Valid @RequestBody request: ArticleUpdateRequest): ArticleResponse {
        return articleService.updateArticle(id, request)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{id}")
    fun deleteArticle(@PathVariable("id") id: Long,
                      @Valid @RequestBody request: ArticleDeleteRequest
    ) {
        articleService.deleteArticle(id, request)
    }
}