package com.group.blogapp.user.dto.request

import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class UserSigninRequest(
    @ApiModelProperty(example = "email@urssu.com")
    @field:NotBlank(message = "Email is required.")
    @field:Email(message = "Invalid email format.")
    val email: String,

    @ApiModelProperty(example = "password")
    @field:NotBlank(message = "Password is required.")
    val password: String
) {
}