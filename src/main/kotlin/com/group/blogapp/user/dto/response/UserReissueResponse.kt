package com.group.blogapp.user.dto.response

data class UserReissueResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(accessToken: String, refreshToken: String): UserReissueResponse {
            return UserReissueResponse(
                accessToken = "Bearer $accessToken",
                refreshToken = "Bearer $refreshToken"
            )
        }
    }
}