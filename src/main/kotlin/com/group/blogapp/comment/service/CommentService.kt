package com.group.blogapp.comment.service

import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.comment.dto.request.CommentCreateRequest
import com.group.blogapp.comment.dto.request.CommentUpdateRequest
import com.group.blogapp.comment.dto.response.CommentResponse

interface CommentService {
    fun saveComment(authInfo: AuthInfo, articleId: Long, request: CommentCreateRequest): CommentResponse
    fun updateComment(articleId: Long, commentId: Long, request: CommentUpdateRequest): CommentResponse
    fun deleteComment(articleId: Long, commentId: Long)
}