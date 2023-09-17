package com.group.blogapp.service.article.service

import com.group.blogapp.article.domain.Article
import com.group.blogapp.article.domain.ArticleRepository
import com.group.blogapp.article.dto.request.ArticleCreateRequest
import com.group.blogapp.article.dto.request.ArticleDeleteRequest
import com.group.blogapp.article.dto.request.ArticleUpdateRequest
import com.group.blogapp.article.exception.ArticleNotFoundException
import com.group.blogapp.user.exception.UserAuthenticationException
import com.group.blogapp.article.service.ArticleService
import com.group.blogapp.comment.domain.Comment
import com.group.blogapp.comment.domain.CommentRepository
import com.group.blogapp.user.domain.User
import com.group.blogapp.user.domain.UserRepository
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest
@DirtiesContext
class ArticleServiceTest @Autowired constructor(
    private val articleService: ArticleService,
    private val articleRepository: ArticleRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        articleRepository.deleteAll()
    }

    @Test
    @DisplayName("게시글 작성 - 성공")
    fun saveArticle_successTest() {
        // given
        val savedUser = userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val request = ArticleCreateRequest("email@urssu.com", "password", "title", "content")

        // when
        articleService.saveArticle(request)

        // then
        val results = articleRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].title).isEqualTo(request.title)
        assertThat(results[0].content).isEqualTo(request.content)
        assertThat(results[0].user?.id).isEqualTo(savedUser.id)
    }

    @Test
    @DisplayName("게시글 작성 - 요청자가 인증되지 않은 경우 실패")
    fun saveArticle_failUnauthorizedUserRequest() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val request = ArticleCreateRequest("email1@urssu.com", "password", "title", "content")

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            articleService.saveArticle(request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }

    @Test
    @DisplayName("게시글 수정 - 성공")
    fun updateArticle_success() {
        // given
        val savedUser = userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))

        val article = Article("title", "content")
        article.user = savedUser
        val savedArticle = articleRepository.save(article)
        val request = ArticleUpdateRequest("email@urssu.com", "password", "title1", "content1")
        val articleId = savedArticle.id!!

        // when
        articleService.updateArticle(articleId, request)

        // then
        val result = articleRepository.findAll()[0]
        assertThat(result.title).isEqualTo(request.title)
        assertThat(result.content).isEqualTo(request.content)
    }

    @Test
    @DisplayName("게시글 수정 - 요청자가 인증되지 않은 경우 실패")
    fun updateArticle_failUnauthorizedUserRequest() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        articleRepository.save(Article("title", "content"))
        val request = ArticleUpdateRequest("email1@urssu.com", "password", "title1", "content1")
        val articleId = 1L

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            articleService.updateArticle(articleId, request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }

    @Test
    @DisplayName("게시글 수정 - 게시글을 찾을 수 없는 경우 실패")
    fun updateArticle_failArticleNotFound() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        articleRepository.save(Article("title", "content"))
        val request = ArticleUpdateRequest("email@urssu.com", "password", "title1", "content1")
        val articleId = 1234L

        // when & then
        val message = assertThrows<ArticleNotFoundException> {
            articleService.updateArticle(articleId, request)
        }.message
        assertThat(message).isEqualTo("Article is not found.")
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    fun deleteArticle_success() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))
        val comment = Comment("content")
        comment.article = savedArticle
        commentRepository.save(comment)
        val request = ArticleDeleteRequest("email@urssu.com", "password")
        val articleId = savedArticle.id!!

        // when
        articleService.deleteArticle(articleId, request)

        // then
        assertThat(articleRepository.findAll()).isEmpty()
        assertThat(commentRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("게시글 삭제 - 요청자가 인증되지 않은 경우 실패")
    fun deleteArticle_failUnauthorizedUserRequest() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))
        val comment = Comment("content")
        comment.article = savedArticle
        commentRepository.save(comment)
        val request = ArticleDeleteRequest("email1@urssu.com", "password")
        val articleId = savedArticle.id!!

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            articleService.deleteArticle(articleId, request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }

    @Test
    @DisplayName("게시글 삭제 - 게시글을 찾을 수 없는 경우 실패")
    fun deleteArticle_failArticleNotFound() {
        // given
        userRepository.save(User("email1@urssu.com", passwordEncoder.encode("password"), "username"))
        val savedArticle = articleRepository.save(Article("title", "content"))
        val comment = Comment("content")
        comment.article = savedArticle
        commentRepository.save(comment)
        val request = ArticleDeleteRequest("email1@urssu.com", "password")
        val articleId = 1234L

        // when & then
        val message = assertThrows<ArticleNotFoundException> {
            articleService.deleteArticle(articleId, request)
        }.message
        assertThat(message).isEqualTo("Article is not found.")
    }
}