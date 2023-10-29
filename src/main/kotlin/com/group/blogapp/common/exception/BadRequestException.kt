package com.group.blogapp.common.exception

import org.springframework.http.HttpStatus

abstract class BadRequestException(
    message: String,
) : ApiException(message, HttpStatus.BAD_REQUEST) {
}