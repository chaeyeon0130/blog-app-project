package com.group.blogapp.user.service

import com.group.blogapp.user.domain.User
import com.group.blogapp.user.dto.request.UserSigninRequest
import com.group.blogapp.user.dto.request.UserSignupRequest
import com.group.blogapp.user.dto.response.UserSigninResponse
import com.group.blogapp.user.dto.response.UserSignupResponse

interface UserService {
    fun signup(request: UserSignupRequest): UserSignupResponse
    fun authenticateUser(email: String, password: String): User?
    fun findUser(email: String): User
    fun signin(request: UserSigninRequest): UserSigninResponse
    fun deleteUser(email: String)
}