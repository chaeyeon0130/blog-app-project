package com.group.blogapp.user.dto.response

import com.group.blogapp.user.domain.Role
import com.group.blogapp.user.domain.User

data class UserSignupResponse(
    val email: String,
    val username: String,
    val role: Role
) {
    companion object {
        fun of(user: User): UserSignupResponse {
            return UserSignupResponse(
                email = user.email,
                username = user.username,
                role = user.role
            )
        }
    }
}