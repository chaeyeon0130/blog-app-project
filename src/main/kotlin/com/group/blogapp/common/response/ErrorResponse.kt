package com.group.blogapp.common.response

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class ErrorResponse(
    val time: LocalDateTime,
    val status: HttpStatus,
    val message: String?,
    val requestURI: String) {
}