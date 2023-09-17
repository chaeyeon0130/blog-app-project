package com.group.blogapp.config.handler

import com.group.blogapp.common.exception.ApiException
import com.group.blogapp.common.response.ErrorResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.util.*

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    protected fun handleApiException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<Any>? {
        if (!Objects.nonNull(exception))
            return null
        if (exception is ApiException) {
            return handleExceptionInternal(exception, null, HttpHeaders(), exception.status, request)
        }
        else {
            exception.printStackTrace()
            return handleExceptionInternal(exception, null, HttpHeaders(), INTERNAL_SERVER_ERROR, request)
        }
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val servletWebRequest = request as ServletWebRequest
        val response = ErrorResponse(
            time = LocalDateTime.now(),
            status = status,
            message = ex.bindingResult.fieldErrors.get(0).defaultMessage,
            requestURI = servletWebRequest.request.requestURI
        )

        return ResponseEntity(response, headers, status)
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val servletWebRequest = request as ServletWebRequest
        val response: ErrorResponse

        if (status == INTERNAL_SERVER_ERROR) {
            response = ErrorResponse(time = LocalDateTime.now(), status = status, message = "Internal Server Error", requestURI = servletWebRequest.request.requestURI)
        }
        else {
            response = ErrorResponse(time = LocalDateTime.now(), status = status, message = ex.message, requestURI = servletWebRequest.request.requestURI)
        }


        return ResponseEntity(response, headers, status)
    }
}