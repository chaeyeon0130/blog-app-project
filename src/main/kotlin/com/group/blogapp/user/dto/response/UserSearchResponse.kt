package com.group.blogapp.user.dto.response

import com.group.blogapp.user.domain.Role
import com.querydsl.core.annotations.QueryProjection
import java.time.LocalDateTime

data class UserSearchResponse @QueryProjection constructor(
    val id: Long,
    val email: String,
    val role: Role,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
}