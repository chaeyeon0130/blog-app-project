package com.group.blogapp.comment.controller

import com.group.blogapp.comment.dto.request.CommentCreateRequest
import com.group.blogapp.comment.dto.request.CommentDeleteRequest
import com.group.blogapp.comment.dto.request.CommentUpdateRequest
import com.group.blogapp.comment.dto.response.CommentResponse
import com.group.blogapp.comment.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping
class CommentController(
    private val commentService: CommentService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/articles/{articleId}/comments")
    fun saveComment(@PathVariable("articleId") articleId: Long,
        @Valid @RequestBody request: CommentCreateRequest): CommentResponse {
        return commentService.saveComment(articleId, request)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/articles/{articleId}/comments/{commentId}")
    fun updateComment(@PathVariable("articleId") articleId: Long,
                      @PathVariable("commentId") commentId: Long,
                      @Valid @RequestBody request: CommentUpdateRequest
    ): CommentResponse {
        return commentService.updateComment(articleId, commentId, request)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    fun updateComment(@PathVariable("articleId") articleId: Long,
                      @PathVariable("commentId") commentId: Long,
                      @Valid @RequestBody request: CommentDeleteRequest
    ) {
        commentService.deleteComment(articleId, commentId, request)
    }
}