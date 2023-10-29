package com.group.blogapp.user.exception

import com.group.blogapp.common.exception.NotFoundException

class UserNotFoundException(
    message: String
) : NotFoundException(message){
}