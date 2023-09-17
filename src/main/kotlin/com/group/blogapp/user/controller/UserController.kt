package com.group.blogapp.user.controller

import com.group.blogapp.user.dto.request.UserCreateRequest
import com.group.blogapp.user.dto.request.UserDeleteRequest
import com.group.blogapp.user.dto.response.UserResponse
import com.group.blogapp.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping
class UserController(
    private val userService: UserService
) {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users")
    fun saveUser(@Valid @RequestBody request: UserCreateRequest): UserResponse {
        return userService.saveUser(request)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/users")
    fun deleteUser(@Valid @RequestBody request: UserDeleteRequest) {
        userService.deleteUser(request)
    }
}