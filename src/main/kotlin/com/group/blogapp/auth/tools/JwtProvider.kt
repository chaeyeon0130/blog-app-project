package com.group.blogapp.auth.tools

import com.group.blogapp.auth.tools.JwtType.*
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties
) {
    private fun createToken(claims: Map<String, Any>, exp: Int): String {
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + exp))
            .signWith(Keys.hmacShaKeyFor(jwtProperties.secret!!.toByteArray(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateAccessToken(email: String, role: String): String {
        val claims = mapOf<String, Any>(
            "email" to email,
            "type" to ACCESS.type,
            "role" to role
        )

        return createToken(claims, jwtProperties.accessTokenExp)
    }

    fun generateRefreshToken(email: String, role: String): String {
        val claims = mapOf<String, Any>(
            "email" to email,
            "type" to REFRESH.type,
            "role" to role
        )

        return createToken(claims, jwtProperties.refreshTokenExp)
    }
}