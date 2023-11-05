package com.group.blogapp.comment.controller

import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.comment.dto.request.CommentCreateRequest
import com.group.blogapp.comment.dto.request.CommentUpdateRequest
import com.group.blogapp.comment.dto.response.CommentResponse
import com.group.blogapp.comment.service.CommentService
import com.group.blogapp.config.annotation.Auth
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api(tags = ["Comment"])
@RestController
@RequestMapping
class CommentController(
    private val commentService: CommentService
) {
    @Operation(summary = "Write Comment", description = "Write Comment Api")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Write Comment Success"),
        ApiResponse(responseCode = "404", description = "User is not found."),
        ApiResponse(responseCode = "404", description = "Article is not found.")
    )
    @ApiImplicitParam(
        name = "articleId",
        value = "Article Index",
        required = true,
        dataType = "Long",
        paramType = "path",
        defaultValue = "None"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles/{articleId}/comments")
    fun saveComment(@ApiIgnore @Auth authInfo: AuthInfo,
                    @PathVariable("articleId") articleId: Long,
                    @Valid @RequestBody request: CommentCreateRequest): CommentResponse {
        return commentService.saveComment(authInfo, articleId, request)
    }

    @Operation(summary = "Update Comment", description = "Update Comment Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Update Comment Success"),
        ApiResponse(responseCode = "404", description = "Comment is not found.")
    )
    @ApiImplicitParams(
        ApiImplicitParam(
            name = "articleId",
            value = "Article Index",
            required = true,
            dataType = "Long",
            paramType = "path",
            defaultValue = "None"
        ),
        ApiImplicitParam(
            name = "commentId",
            value = "Comment Index",
            required = true,
            dataType = "Long",
            paramType = "path",
            defaultValue = "None"
        )
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/articles/{articleId}/comments/{commentId}")
    fun updateComment(@PathVariable("articleId") articleId: Long,
                      @PathVariable("commentId") commentId: Long,
                      @Valid @RequestBody request: CommentUpdateRequest
    ): CommentResponse {
        return commentService.updateComment(articleId, commentId, request)
    }

    @Operation(summary = "Delete Comment", description = "Delete Comment Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Delete Comment Success"),
        ApiResponse(responseCode = "404", description = "Comment is not found.")
    )
    @ApiImplicitParams(
        ApiImplicitParam(
            name = "articleId",
            value = "Article Index",
            required = true,
            dataType = "Long",
            paramType = "path",
            defaultValue = "None"
        ),
        ApiImplicitParam(
            name = "commentId",
            value = "Comment Index",
            required = true,
            dataType = "Long",
            paramType = "path",
            defaultValue = "None"
        )
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    fun deleteComment(@PathVariable("articleId") articleId: Long,
                      @PathVariable("commentId") commentId: Long
    ) {
        commentService.deleteComment(articleId, commentId)
    }
}