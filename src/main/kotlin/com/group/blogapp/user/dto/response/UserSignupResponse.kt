package com.group.blogapp.user.dto.response

import com.group.blogapp.user.domain.Role
import com.group.blogapp.user.domain.User
import io.swagger.annotations.ApiModelProperty

data class UserSignupResponse(
    @ApiModelProperty(example = "email@urssu.com")
    val email: String,
    @ApiModelProperty(example = "username")
    val username: String,
    @ApiModelProperty(example = "USER")
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