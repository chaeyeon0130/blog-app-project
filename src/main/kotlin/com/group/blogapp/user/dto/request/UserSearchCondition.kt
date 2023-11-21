package com.group.blogapp.user.dto.request

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class UserSearchCondition(
    val username: String?,
    val email: String?,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val createdAtStart: LocalDate?,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val createdAtEnd: LocalDate?,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val updatedAtStart: LocalDate?,
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    val updatedAtEnd: LocalDate?
) {
}