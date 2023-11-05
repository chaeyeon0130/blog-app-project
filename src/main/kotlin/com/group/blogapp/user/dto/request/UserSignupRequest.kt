package com.group.blogapp.user.dto.request

import com.group.blogapp.user.domain.Role
import com.group.blogapp.user.domain.User
import com.group.blogapp.user.exception.UserBadRequestException
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserSignupRequest(
    @ApiModelProperty(example = "email@urssu.com")
    @field:NotBlank(message = "Email is required.")
    @field:Email(message = "Invalid email format.")
    val email: String,

    @ApiModelProperty(example = "password")
    @field:NotBlank(message = "Password is required.")
    val password: String,

    @ApiModelProperty(example = "username")
    @field:NotBlank(message = "Username is required.")
    val username: String,

    @ApiModelProperty(example = "USER")
    @field:NotBlank(message = "Role is required.")
    val role: String
) {
    fun toEntity(): User {
        return User(
            email,
            password,
            username,
            when (role) {
                "USER" -> Role.USER
                "ADMIN" -> Role.ADMIN
                else -> throw UserBadRequestException("Role must be \"USER\" or \"ADMIN\".")
            }
        )
    }
}