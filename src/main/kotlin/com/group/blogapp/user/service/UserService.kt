package com.group.blogapp.user.service

import com.group.blogapp.user.domain.User
import com.group.blogapp.user.dto.request.UserCreateRequest
import com.group.blogapp.user.dto.request.UserDeleteRequest
import com.group.blogapp.user.dto.response.UserResponse

interface UserService {
    fun saveUser(request: UserCreateRequest): UserResponse
    fun authenticateUser(email: String, password: String): User?
    fun deleteUser(request: UserDeleteRequest)
}