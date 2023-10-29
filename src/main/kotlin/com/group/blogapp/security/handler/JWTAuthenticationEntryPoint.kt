package com.group.blogapp.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.group.blogapp.common.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JWTAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val errorResponse = ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED,
            authException!!.message, request!!.requestURI)

        response!!.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.writer.println(convertAnyToJson(errorResponse))
    }

    private fun convertAnyToJson(any: Any): String {
        val mapper = ObjectMapper().registerModule(JavaTimeModule())
        return mapper.writeValueAsString(any)
    }
}