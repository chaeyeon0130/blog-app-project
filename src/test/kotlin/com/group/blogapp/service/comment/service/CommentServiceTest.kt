package com.group.blogapp.service.comment.service

import com.group.blogapp.article.domain.Article
import com.group.blogapp.article.domain.ArticleRepository
import com.group.blogapp.article.exception.ArticleNotFoundException
import com.group.blogapp.comment.domain.Comment
import com.group.blogapp.comment.domain.CommentRepository
import com.group.blogapp.comment.dto.request.CommentCreateRequest
import com.group.blogapp.comment.dto.request.CommentDeleteRequest
import com.group.blogapp.comment.dto.request.CommentUpdateRequest
import com.group.blogapp.comment.exception.CommentNotFoundException
import com.group.blogapp.comment.service.CommentService
import com.group.blogapp.user.domain.User
import com.group.blogapp.user.domain.UserRepository
import com.group.blogapp.user.exception.UserAuthenticationException
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class CommentServiceTest @Autowired constructor(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        articleRepository.deleteAll()
        commentRepository.deleteAll()
    }

    @Test
    @DisplayName("댓글 작성 - 성공")
    fun saveComment_success() {
        // given
        val savedUser = userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))
        val request = CommentCreateRequest("email@urssu.com", "password", "content")
        val articleId = savedArticle.id!!

        // when
        commentService.saveComment(articleId, request)

        // then
        val results = commentRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].content).isEqualTo(request.content)
        assertThat(results[0].user?.id).isEqualTo(savedUser.id)
    }

    @Test
    @DisplayName("댓글 작성 - 요청자가 인증되지 않은 경우 실패")
    fun saveComment_failUnauthorizedUserRequest() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))
        val request = CommentCreateRequest("email1@urssu.com", "password", "content")
        val articleId = savedArticle.id!!

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            commentService.saveComment(articleId, request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }

    @Test
    @DisplayName("댓글 작성 - 게시글을 찾을 수 없는 경우 실패")
    fun saveComment_failArticleNotFound() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        articleRepository.save(Article("title", "content"))
        val request = CommentCreateRequest("email@urssu.com", "password", "content")
        val articleId = 1234L

        // when & then
        val message = assertThrows<ArticleNotFoundException> {
            commentService.saveComment(articleId, request)
        }.message
        assertThat(message).isEqualTo("Article is not found.")
    }

    @Test
    @DisplayName("댓글 수정 - 성공")
    fun updateComment_success() {
        // given
        val savedUser = userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))

        val savedArticle = articleRepository.save(Article("title", "content"))

        val comment = Comment("content")
        comment.article = savedArticle
        comment.user = savedUser
        val savedComment = commentRepository.save(comment)

        val request = CommentUpdateRequest("email@urssu.com", "password", "content1")
        val articleId = savedArticle.id!!
        val commentId = savedComment.id!!

        // when
        commentService.updateComment(articleId, commentId, request)

        // then
        val result = commentRepository.findAll()[0]
        assertThat(result.content).isEqualTo(request.content)
    }

    @Test
    @DisplayName("댓글 작성 - 요청자가 인증되지 않은 경우 실패")
    fun updateComment_failUnauthorizedUserRequest() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))

        val comment = Comment("content")
        comment.article = savedArticle
        val savedComment = commentRepository.save(comment)

        val request = CommentUpdateRequest("email1@urssu.com", "password", "content1")
        val articleId = savedArticle.id!!
        val commentId = savedComment.id!!

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            commentService.updateComment(articleId, commentId, request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }

    @Test
    @DisplayName("댓글 수정 - 요청으로 들어온 게시글 id/댓글 id를 가진 댓글을 찾을 수 없는 경우 실패")
    fun updateComment_failCommentNotFound() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))

        val comment = Comment("content")
        comment.article = savedArticle
        val savedComment = commentRepository.save(comment)

        val request = CommentUpdateRequest("email@urssu.com", "password", "content1")
        val articleId = 1234L
        val commentId = savedComment.id!!

        // when & then
        val message = assertThrows<CommentNotFoundException> {
            commentService.updateComment(articleId, commentId, request)
        }.message
        assertThat(message).isEqualTo("Comment is not found.")
    }

    @Test
    @DisplayName("댓글 삭제 - 성공")
    fun deleteComment_success() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))

        val comment = Comment("content")
        comment.article = savedArticle
        val savedComment = commentRepository.save(comment)

        val request = CommentDeleteRequest("email@urssu.com", "password")
        val articleId = savedArticle.id!!
        val commentId = savedComment.id!!

        // when
        commentService.deleteComment(articleId, commentId, request)

        // then
        assertThat(commentRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("댓글 삭제 - 요청자가 인증되지 않은 경우 실패")
    fun deleteComment_failUnauthorizedUserRequest() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))

        val comment = Comment("content")
        comment.article = savedArticle
        val savedComment = commentRepository.save(comment)

        val request = CommentDeleteRequest("email1@urssu.com", "password")
        val articleId = savedArticle.id!!
        val commentId = savedComment.id!!

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            commentService.deleteComment(articleId, commentId, request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }

    @Test
    @DisplayName("댓글 삭제 - 요청으로 들어온 게시글 id/댓글 id를 가진 댓글을 찾을 수 없는 경우 실패")
    fun deleteComment_failCommentNotFound() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))

        val comment = Comment("content")
        comment.article = savedArticle
        val savedComment = commentRepository.save(comment)

        val request = CommentDeleteRequest("email@urssu.com", "password")
        val articleId = 1234L
        val commentId = savedComment.id!!

        // when & then
        val message = assertThrows<CommentNotFoundException> {
            commentService.deleteComment(articleId, commentId, request)
        }.message
        assertThat(message).isEqualTo("Comment is not found.")
    }
}