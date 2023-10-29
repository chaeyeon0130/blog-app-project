package com.group.blogapp.security.filter

import com.group.blogapp.auth.exception.AuthenticationException
import com.group.blogapp.auth.tools.JwtVerifier
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTRequestFilter(
    private val jwtVerifier: JwtVerifier,
    private val entryPoint: AuthenticationEntryPoint
) : OncePerRequestFilter() {
    companion object {
        private const val BEARER_SCHEME = "Bearer"
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER) ?:
                        throw AuthenticationException("Authorization Header is missing.")

            val token = extractAccessToken(authorizationHeader)
            val authentication = jwtVerifier.verify(token, true)
            val context = SecurityContextHolder.getContext()
            context.authentication = authentication
            filterChain.doFilter(request, response)
        } catch (exception: AuthenticationException) {
            entryPoint.commence(request, response, exception)
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val excludePath = listOf("/users/signup", "/users/signin")
        val path = request.requestURI

        return excludePath.any(path::startsWith)
    }

    private fun validateAuthorizationHeader(splits: List<String>) {
        if (splits.size != 2) {
            throw AuthenticationException("Authorization Header is malformed.")
        }
        val scheme = splits[0]
        if (scheme != BEARER_SCHEME) {
            throw AuthenticationException("Scheme is not Bearer.")
        }
    }

    private fun extractAccessToken(authorizationHeader: String): String {
        val splits = authorizationHeader.split(" ")
        validateAuthorizationHeader(splits)
        return splits[1]
    }
}