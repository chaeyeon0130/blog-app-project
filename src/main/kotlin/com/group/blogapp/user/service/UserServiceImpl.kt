package com.group.blogapp.user.service

import com.group.blogapp.auth.tools.JwtProvider
import com.group.blogapp.user.domain.User
import com.group.blogapp.user.domain.UserRepository
import com.group.blogapp.user.dto.request.UserSignupRequest
import com.group.blogapp.user.dto.request.UserSigninRequest
import com.group.blogapp.user.dto.response.UserSigninResponse
import com.group.blogapp.user.dto.response.UserSignupResponse
import com.group.blogapp.user.exception.UserAuthenticationException
import com.group.blogapp.user.exception.UserNotFoundException
import com.group.blogapp.user.exception.UserSignupException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : UserService{

    @Transactional
    override fun signup(request: UserSignupRequest): UserSignupResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserSignupException("Email is duplicated.")
        }

        if (userRepository.existsByUsername(request.username)) {
            throw UserSignupException("Username is duplicated.")
        }

        val newUser = userRepository.save(request.toEntity())
        newUser.encodePassword(passwordEncoder)

        return UserSignupResponse.of(newUser)
    }

    @Transactional(readOnly = true)
    override fun authenticateUser(email: String, password: String): User?{
        val foundUser = userRepository.findByEmail(email)

        if (foundUser == null || !passwordEncoder.matches(password, foundUser.password)) {
            throw UserAuthenticationException("User is not authorized.")
        }
        return foundUser
    }

    @Transactional(readOnly = true)
    override fun findUser(email: String): User {
        return userRepository.findByEmail(email) ?: throw UserNotFoundException("User is not found.")
    }

    @Transactional
    override fun signin(request: UserSigninRequest): UserSigninResponse {
        val foundUser = authenticateUser(request.email, request.password)

        val accessToken = jwtProvider.generateAccessToken(foundUser!!.email, foundUser.role.role)
        val refreshToken = jwtProvider.generateRefreshToken(foundUser.email, foundUser.role.role)

        foundUser.addRefreshToken(refreshToken)

        return UserSigninResponse.of(foundUser, accessToken)
    }

    @Transactional
    override fun deleteUser(email: String) {
        val foundUser = findUser(email)
        userRepository.delete(foundUser)
    }
}