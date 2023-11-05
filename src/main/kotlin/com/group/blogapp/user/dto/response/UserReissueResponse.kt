package com.group.blogapp.user.dto.response

import io.swagger.annotations.ApiModelProperty

data class UserReissueResponse(
    @ApiModelProperty(example = "Bearer eyJhbGciOiJIUzI1NiJ9")
    val accessToken: String,
    @ApiModelProperty(example = "Bearer eyJhbGciOiJIUzI1NiJ9")
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