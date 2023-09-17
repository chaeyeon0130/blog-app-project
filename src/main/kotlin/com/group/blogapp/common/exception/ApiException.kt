package com.group.blogapp.common.exception

import org.springframework.http.HttpStatus

abstract class ApiException(
    message: String,
    val status: HttpStatus
) : Exception(message) {

}