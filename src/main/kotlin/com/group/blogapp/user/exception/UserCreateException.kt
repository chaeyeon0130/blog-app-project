package com.group.blogapp.user.exception

import com.group.blogapp.common.exception.ConflictException

class UserCreateException(
    message: String
) : ConflictException(message) {
}