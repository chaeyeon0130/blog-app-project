package com.group.blogapp.user.dto.response

import com.group.blogapp.user.domain.Role
import com.group.blogapp.user.domain.User
import io.swagger.annotations.ApiModelProperty

data class UserSigninResponse(
    @ApiModelProperty(example = "email@urssu.com")
    val email: String,
    @ApiModelProperty(example = "username")
    val username: String,
    @ApiModelProperty(example = "USER")
    val role: Role,
    @ApiModelProperty(example = "Bearer eyJhbGciOiJIUzI1NiJ9")
    val accessToken: String,
    @ApiModelProperty(example = "Bearer eyJhbGciOiJIUzI1NiJ9")
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