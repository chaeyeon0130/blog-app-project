package com.group.blogapp.user.controller

import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.config.annotation.Auth
import com.group.blogapp.user.dto.request.UserSigninRequest
import com.group.blogapp.user.dto.request.UserSignupRequest
import com.group.blogapp.user.dto.response.UserReissueResponse
import com.group.blogapp.user.dto.response.UserSigninResponse
import com.group.blogapp.user.dto.response.UserSignupResponse


import com.group.blogapp.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun singup(@Valid @RequestBody request: UserSignupRequest): UserSignupResponse {
        return userService.signup(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    fun signin(@Valid @RequestBody request: UserSigninRequest): UserSigninResponse {
        return userService.signin(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    fun deleteUser(@Auth authInfo: AuthInfo) {
        userService.deleteUser(authInfo)
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reissue")
    fun reissue(@Auth authInfo: AuthInfo): UserReissueResponse {
        return userService.reissue(authInfo)
    }
}