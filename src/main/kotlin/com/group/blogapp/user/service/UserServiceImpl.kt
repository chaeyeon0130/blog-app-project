package com.group.blogapp.user.service

import com.group.blogapp.user.domain.User
import com.group.blogapp.user.domain.UserRepository
import com.group.blogapp.user.dto.request.UserCreateRequest
import com.group.blogapp.user.dto.request.UserDeleteRequest
import com.group.blogapp.user.dto.response.UserResponse
import com.group.blogapp.user.exception.UserAuthenticationException
import com.group.blogapp.user.exception.UserCreateException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService{

    @Transactional
    override fun saveUser(request: UserCreateRequest): UserResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserCreateException("Email is duplicated.")
        }

        if (userRepository.existsByUsername(request.username)) {
            throw UserCreateException("Username is duplicated.")
        }

        val newUser = User(request.email, passwordEncoder.encode(request.password), request.username)
        userRepository.save(newUser)

        return UserResponse.of(newUser)
    }

    @Transactional
    override fun deleteUser(request: UserDeleteRequest) {
        val foundUser = authenticateUser(request.email, request.password)
        return userRepository.delete(foundUser!!)
    }

    @Transactional(readOnly = true)
    override fun authenticateUser(email: String, password: String): User?{
        val foundUser = userRepository.findByEmail(email)

        if (foundUser == null || !passwordEncoder.matches(password, foundUser.password)) {
            throw UserAuthenticationException("User is not authorized.")
        }
        return foundUser
    }
}