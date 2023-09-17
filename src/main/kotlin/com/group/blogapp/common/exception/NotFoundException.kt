package com.group.blogapp.common.exception

import org.springframework.http.HttpStatus

abstract class NotFoundException(
    message: String,
) : ApiException(message, HttpStatus.NOT_FOUND) {
}