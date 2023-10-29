package com.group.blogapp.user.dto.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserSigninRequest(
    @field:NotBlank(message = "Email is required.")
    @field:Email(message = "Invalid email format.")
    val email: String,

    @field:NotBlank(message = "Password is required.")
    val password: String
) {
}