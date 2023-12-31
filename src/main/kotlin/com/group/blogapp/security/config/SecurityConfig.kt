package com.group.blogapp.security.config

import com.group.blogapp.auth.tools.JwtVerifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtVerifier: JwtVerifier,
    private val entryPoint: AuthenticationEntryPoint
) {
    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(entryPoint)
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/users/signin").permitAll()
            .antMatchers("/users/signup").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .antMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
            .anyRequest().authenticated() // 그 외 인증 없이 접근 X
            .and()
            .apply(JWTRequestSecurityConfig(jwtVerifier, entryPoint))

        return httpSecurity.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer {
            web: WebSecurity ->
            web.ignoring()
                .antMatchers(
                    "/v3/api-docs",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/actuator/**"
                )
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}