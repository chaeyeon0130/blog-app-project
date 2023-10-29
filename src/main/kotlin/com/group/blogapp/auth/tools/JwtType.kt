package com.group.blogapp.auth.tools

enum class JwtType(
    val type: String
) {
    ACCESS("access"),
    REFRESH("refresh")
}