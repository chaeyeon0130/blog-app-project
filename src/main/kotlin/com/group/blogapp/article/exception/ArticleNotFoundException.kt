package com.group.blogapp.article.exception

import com.group.blogapp.common.exception.NotFoundException

class ArticleNotFoundException(
    message: String
) : NotFoundException(message) {
}