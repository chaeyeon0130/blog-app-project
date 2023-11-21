package com.group.blogapp.user.controller

import com.group.blogapp.auth.dto.AuthInfo
import com.group.blogapp.config.annotation.Auth
import com.group.blogapp.user.dto.request.UserSearchCondition
import com.group.blogapp.user.dto.request.UserSigninRequest
import com.group.blogapp.user.dto.request.UserSignupRequest
import com.group.blogapp.user.dto.response.UserReissueResponse
import com.group.blogapp.user.dto.response.UserSearchResponse
import com.group.blogapp.user.dto.response.UserSigninResponse
import com.group.blogapp.user.dto.response.UserSignupResponse


import com.group.blogapp.user.service.UserService
import io.swagger.annotations.Api
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore
import javax.validation.Valid

@Api(tags = ["User"])
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @Operation(summary = "Signup", description = "User Signup Api")
    @ApiResponses(
        ApiResponse(responseCode = "201", description = "Signup Success"),
        ApiResponse(responseCode = "409", description = "Email or Username is duplicated.")
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    fun singup(@Valid @RequestBody request: UserSignupRequest): UserSignupResponse {
        return userService.signup(request)
    }

    @Operation(summary = "Signin", description = "User Signin Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Signin Success"),
        ApiResponse(responseCode = "401", description = "User is not authorized.")
    )
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/signin")
    fun signin(@Valid @RequestBody request: UserSigninRequest): UserSigninResponse {
        return userService.signin(request)
    }

    @Operation(summary = "Delete", description = "User Delete Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Delete Success"),
        ApiResponse(responseCode = "404", description = "User is not found.")
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    fun deleteUser(@ApiIgnore @Auth authInfo: AuthInfo) {
        userService.deleteUser(authInfo)
    }

    @Operation(summary = "Token Reissue", description = "User Token Reissue Api")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Token Reissue Success"),
        ApiResponse(responseCode = "404", description = "User is not found.")
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/reissue")
    fun reissue(@ApiIgnore @Auth authInfo: AuthInfo): UserReissueResponse {
        return userService.reissue(authInfo)
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    fun searchUser(condition: UserSearchCondition): List<UserSearchResponse> {
        return userService.searchUser(condition)
    }
}