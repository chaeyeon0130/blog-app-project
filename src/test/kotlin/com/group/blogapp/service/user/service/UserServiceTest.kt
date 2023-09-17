package com.group.blogapp.service.user.service

import com.group.blogapp.article.domain.Article
import com.group.blogapp.article.domain.ArticleRepository
import com.group.blogapp.comment.domain.Comment
import com.group.blogapp.comment.domain.CommentRepository
import com.group.blogapp.user.domain.User
import com.group.blogapp.user.domain.UserRepository
import com.group.blogapp.user.dto.request.UserCreateRequest
import com.group.blogapp.user.dto.request.UserDeleteRequest
import com.group.blogapp.user.exception.UserAuthenticationException
import com.group.blogapp.user.exception.UserCreateException
import com.group.blogapp.user.service.UserService
import com.group.blogapp.user.service.UserServiceImpl
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val articleRepository: ArticleRepository,
    private val commentRepository: CommentRepository,
    private val passwordEncoder: PasswordEncoder
){
    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }

    @Test
    @DisplayName("회원가입 - 성공")
    fun saveUser_successTest() {
        // given
        val request = UserCreateRequest("email@urssu.com", "password", "username")

        // when
        userService.saveUser(request)

        // then
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].email).isEqualTo(request.email)
        assertTrue(passwordEncoder.matches(request.password, results[0].password))
        assertThat(results[0].username).isEqualTo(request.username)
    }

    @Test
    @DisplayName("회원가입 - 중복된 이메일로 회원 가입 시도한 경우 실패")
    fun saveUser_failDuplicatedEmail() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val request = UserCreateRequest("email@urssu.com", "password1", "username1")

        // when & then
        val message = assertThrows<UserCreateException> {
            userService.saveUser(request)
        }.message
        assertThat(message).isEqualTo("Email is duplicated.")
    }

    @Test
    @DisplayName("회원가입 - 중복된 이름으로 회원 가입 시도한 경우 실패")
    fun saveUser_failDuplicatedUsername() {
        // given
        userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))
        val request = UserCreateRequest("email1@urssu.com", "password1", "username")

        // when & then
        val message = assertThrows<UserCreateException> {
            userService.saveUser(request)
        }.message
        assertThat(message).isEqualTo("Username is duplicated.")
    }

    @Test
    @DisplayName("회원탈퇴 - 성공")
    fun deleteUser_success() {
        // given
        val savedUser = userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))

        val article = Article("title", "comment")
        article.user = savedUser
        articleRepository.save(article)

        val comment = Comment("content")
        comment.user = savedUser
        commentRepository.save(comment)

        val request = UserDeleteRequest("email@urssu.com", "password")

        // when
        userService.deleteUser(request)

        // then
        assertThat(userRepository.findAll()).isEmpty()
        assertThat(articleRepository.findAll()).isEmpty()
        assertThat(commentRepository.findAll()).isEmpty()
    }

    @Test
    @DisplayName("회원탈퇴 - 요청자가 인증되지 않은 경우 실패")
    fun deleteUser_failUnauthorizedUserRequest() {
        // given
        val savedUser = userRepository.save(User("email@urssu.com", passwordEncoder.encode("password"), "username"))

        val article = Article("title", "comment")
        article.user = savedUser
        articleRepository.save(article)

        val comment = Comment("content")
        comment.user = savedUser
        commentRepository.save(comment)

        val request = UserDeleteRequest("email1@urssu.com", "password")

        // when & then
        val message = assertThrows<UserAuthenticationException> {
            userService.deleteUser(request)
        }.message
        assertThat(message).isEqualTo("User is not authorized.")
    }
}