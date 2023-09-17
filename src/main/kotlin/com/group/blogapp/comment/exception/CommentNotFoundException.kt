package com.group.blogapp.comment.exception

import com.group.blogapp.common.exception.NotFoundException

class CommentNotFoundException(
    message: String
) : NotFoundException(message){
}