package com.group.blogapp.user.domain

enum class Role(
    val role: String
) {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN")
}