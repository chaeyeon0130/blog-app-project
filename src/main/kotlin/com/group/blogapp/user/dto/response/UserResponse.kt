package com.group.blogapp.user.dto.response

import com.group.blogapp.user.domain.User

data class UserResponse(
    val email: String,
    val username: String
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                email = user.email,
                username = user.username
            )
        }
    }
}