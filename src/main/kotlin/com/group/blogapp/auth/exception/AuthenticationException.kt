package com.group.blogapp.auth.exception

import org.springframework.security.core.AuthenticationException

class AuthenticationException(
    message: String
) : AuthenticationException(message) {
}