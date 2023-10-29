package com.group.blogapp.user.exception

import com.group.blogapp.common.exception.ConflictException

class UserSignupException(
    message: String
) : ConflictException(message) {
}