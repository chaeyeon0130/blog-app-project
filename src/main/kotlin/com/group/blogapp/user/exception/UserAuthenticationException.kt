package com.group.blogapp.user.exception

import com.group.blogapp.common.exception.UnauthorizedException

class UserAuthenticationException(
    message: String
) : UnauthorizedException(message) {
}