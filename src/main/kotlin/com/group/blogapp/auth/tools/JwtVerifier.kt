package com.group.blogapp.auth.tools

import com.group.blogapp.auth.exception.AuthenticationException
import com.group.blogapp.user.domain.User
import com.group.blogapp.user.domain.UserRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException
import java.util.function.Function
import javax.servlet.http.HttpServletRequest

@Component
class JwtVerifier(
    private val userRepository: UserRepository,
    private val jwtProperties: JwtProperties
) {
    private fun getEmail(claims: Claims): String {
        try {
            return claims["email"] as String
        } catch (e: Exception) {
            throw AuthenticationException("JWT Claim에 email이 없습니다.")
        }
    }

    private fun getType(claims: Claims): String {
        try {
            return claims["type"] as String
        } catch (e: Exception) {
            throw AuthenticationException("JWT Claim에 type이 없습니다.")
        }
    }

    private fun getRole(claims: Claims): String {
        try {
            return claims["role"] as String
        } catch (e: Exception) {
            throw AuthenticationException("JWT Claim에 role이 없습니다.")
        }
    }

    private fun extractAllClaims(token: String): Claims {
        try {
            return Jwts.parserBuilder().setSigningKey(jwtProperties.secret!!.toByteArray())
                .build().parseClaimsJws(token).body
        } catch (expiredJwtException: ExpiredJwtException) {
            throw AuthenticationException("Jwt 토큰이 만료되었습니다.")
        } catch (unsupportedJwtException: UnsupportedJwtException) {
            throw AuthenticationException("지원되지 않는 Jwt 토큰입니다.")
        } catch (malformedJwtException: MalformedJwtException) {
            throw AuthenticationException("잘못된 형식의 Jwt 토큰입니다.")
        } catch (signatureException: SignatureException) {
            throw AuthenticationException("Jwt Signature이 잘못된 값입니다.")
        } catch (illegalArgumentException: IllegalArgumentException) {
            throw AuthenticationException("Jwt 헤더 값이 잘못되었습니다.")
        }
    }

    private fun <T> extractClaim(token: String, claimResolver: Function<Claims, T>): T {
        return claimResolver.apply(extractAllClaims(token))
    }

    private fun extractEmail(token: String): String {
        return extractClaim(token, this::getEmail)
    }

    private fun extractType(token: String): String {
        return extractClaim(token, this::getType)
    }

    private fun extractRole(token: String): String {
        return extractClaim(token, this::getRole)
    }

    @Transactional
    fun verify(token: String): Authentication {
        extractAllClaims(token)
        val type = extractType(token)

        if (type != JwtType.ACCESS.type && type != JwtType.REFRESH.type) {
            throw AuthenticationException("주어진 JWT 토큰의 Type은 Access 또는 Refresh 이어야 합니다.")
        }

        val role: String = extractRole(token)
        val user: User = userRepository.findByEmail(extractEmail(token))
            ?: throw AuthenticationException("유효하지 않은 Email입니다.")

        return UsernamePasswordAuthenticationToken(user, "", listOf(SimpleGrantedAuthority(role)))
    }
}