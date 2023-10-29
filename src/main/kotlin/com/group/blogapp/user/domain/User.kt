package com.group.blogapp.user.domain

import com.group.blogapp.article.domain.Article
import com.group.blogapp.comment.domain.Comment
import com.group.blogapp.common.domain.BaseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(name = "email", nullable = false, unique = true)
    val email: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @Column(name = "refresh_token")
    var refreshToken: String? = null,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val articles: MutableList<Article> = mutableListOf(),

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null
) : BaseEntity() {
    fun encodePassword(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(password)
    }

    fun addRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}