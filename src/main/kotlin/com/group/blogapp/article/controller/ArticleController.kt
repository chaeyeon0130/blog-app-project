package com.group.blogapp.article.controller

import com.group.blogapp.article.dto.request.ArticleCreateRequest
import com.group.blogapp.article.dto.request.ArticleUpdateRequest
import com.group.blogapp.article.dto.response.ArticleResponse
import com.group.blogapp.article.service.ArticleService
import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.config.annotation.Auth
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api(tags = ["Article"])
@RestController
@RequestMapping
class ArticleController(
    private val articleService: ArticleService
) {
    @Operation(summary = "Write Article", description = "Write Article Api")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Post Article Success"),
        ApiResponse(responseCode = "404", description = "User is not found.")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles")
    fun saveArticle(@ApiIgnore @Auth authInfo: AuthInfo,
                    @Valid @RequestBody request: ArticleCreateRequest): ArticleResponse {
        return articleService.saveArticle(authInfo, request)
    }

    @Operation(summary = "Update Article", description = "Update Article Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Update Article Success"),
        ApiResponse(responseCode = "404", description = "Article is not found.")
    )
    @ApiImplicitParam(
        name = "id",
        value = "Article Index",
        required = true,
        dataType = "Long",
        paramType = "path",
        defaultValue = "None"
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/articles/{id}")
    fun updateArticle(@PathVariable("id") id: Long,
                      @Valid @RequestBody request: ArticleUpdateRequest): ArticleResponse {
        return articleService.updateArticle(id, request)
    }

    @Operation(summary = "Delete Article", description = "Delete Article Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Delete Article Success"),
        ApiResponse(responseCode = "404", description = "Article is not found.")
    )
    @ApiImplicitParam(
        name = "id",
        value = "Article Index",
        required = true,
        dataType = "Long",
        paramType = "path",
        defaultValue = "None"
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{id}")
    fun deleteArticle(@PathVariable("id") id: Long) {
        articleService.deleteArticle(id)
    }
}