package com.group.blogapp.comment.service

import com.group.blogapp.article.service.ArticleService
import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.comment.domain.Comment
import com.group.blogapp.comment.domain.CommentQuerydslRepository
import com.group.blogapp.comment.domain.CommentRepository
import com.group.blogapp.comment.dto.request.CommentCreateRequest
import com.group.blogapp.comment.dto.request.CommentUpdateRequest
import com.group.blogapp.comment.dto.response.CommentResponse
import com.group.blogapp.comment.exception.CommentNotFoundException
import com.group.blogapp.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val commentQuerydslRepository: CommentQuerydslRepository,
    private val articleService: ArticleService,
    private val userService: UserService
) : CommentService{
    @Transactional
    override fun saveComment(authInfo: AuthInfo, articleId: Long, request: CommentCreateRequest): CommentResponse {
        val foundUser = userService.findUser(authInfo.email)

        val foundArticle = articleService.findArticle(articleId)

        val newComment = Comment(request.content)
        newComment.article = foundArticle
        newComment.user = foundUser

        val savedComment = commentRepository.save(newComment)

        return CommentResponse.of(savedComment)
    }

    @Transactional
    override fun updateComment(articleId: Long, commentId: Long, request: CommentUpdateRequest): CommentResponse {
        val foundComment = commentQuerydslRepository.findByArticleIdAndCommentId(articleId, commentId) ?: throw CommentNotFoundException("Comment is not found.")

        foundComment.content = request.content

        return CommentResponse.of(foundComment)
    }

    @Transactional
    override fun deleteComment(articleId: Long, commentId: Long) {
        val foundComment = commentQuerydslRepository.findByArticleIdAndCommentId(articleId, commentId) ?: throw CommentNotFoundException("Comment is not found.")

        commentRepository.delete(foundComment)
    }
}