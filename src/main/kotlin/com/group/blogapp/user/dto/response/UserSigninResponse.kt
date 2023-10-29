package com.group.blogapp.user.dto.response

import com.group.blogapp.user.domain.Role
import com.group.blogapp.user.domain.User

data class UserSigninResponse(
    val email: String,
    val username: String,
    val role: Role,
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(user: User, accessToken: String): UserSigninResponse {
            return UserSigninResponse(
                email = user.email,
                username = user.username,
                role = user.role,
                accessToken = "Bearer " + accessToken,
                refreshToken = "Bearer " + user.refreshToken!!
            )
        }
    }
}