package com.group.blogapp.common.exception

import org.springframework.http.HttpStatus.CONFLICT

abstract class ConflictException(
    message: String,
) : ApiException(message, CONFLICT) {
}