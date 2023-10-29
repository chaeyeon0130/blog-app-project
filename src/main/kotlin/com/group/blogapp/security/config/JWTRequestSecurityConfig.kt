package com.group.blogapp.security.config

import com.group.blogapp.auth.tools.JwtVerifier
import com.group.blogapp.security.filter.JWTRequestFilter
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class JWTRequestSecurityConfig(
    private val jwtVerifier: JwtVerifier,
    private val entryPoint: AuthenticationEntryPoint
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(builder: HttpSecurity?) {
        builder!!
            .addFilterBefore(JWTRequestFilter(jwtVerifier, entryPoint), UsernamePasswordAuthenticationFilter::class.java)
    }
}