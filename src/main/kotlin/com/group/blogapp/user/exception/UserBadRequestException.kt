package com.group.blogapp.user.exception

import com.group.blogapp.common.exception.BadRequestException

class UserBadRequestException(
    message: String
) : BadRequestException(message) {
}