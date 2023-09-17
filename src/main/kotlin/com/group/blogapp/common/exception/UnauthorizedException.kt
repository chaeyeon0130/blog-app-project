package com.group.blogapp.common.exception

import org.springframework.http.HttpStatus

abstract class UnauthorizedException(
    message: String,
) : ApiException(message, HttpStatus.UNAUTHORIZED) {
}