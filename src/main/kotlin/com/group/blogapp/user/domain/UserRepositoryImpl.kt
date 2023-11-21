package com.group.blogapp.user.domain

import com.group.blogapp.user.domain.QUser.user
import com.group.blogapp.user.dto.request.UserSearchCondition
import com.group.blogapp.user.dto.response.QUserSearchResponse
import com.group.blogapp.user.dto.response.UserSearchResponse
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Repository
class UserRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : UserRepositoryCustom {
    override fun searchByWhere(condition: UserSearchCondition): List<UserSearchResponse> {
        return queryFactory
            .select(
                QUserSearchResponse(
                user.id,
                user.email,
                user.role,
                user.createdAt,
                user.updatedAt
                )
            )
            .from(user)
            .where(
                usernameEq(condition.username),
                emailEq(condition.email),
                createdAtGoe(condition.createdAtStart),
                createdAtLoe(condition.createdAtEnd),
                user.role.eq(Role.USER)
            )
            .orderBy(user.id.desc())
            .fetch()
    }

    private fun usernameEq(username: String?): BooleanExpression? {
        return if (StringUtils.hasText(username)) {
            user.username.eq(username)
        } else {
            null
        }
    }

    private fun emailEq(email: String?): BooleanExpression? {
        return if (StringUtils.hasText(email)) {
            user.email.eq(email)
        } else {
            null
        }
    }

    private fun createdAtGoe(createdAtStart: LocalDate?): BooleanExpression? {
        return if (createdAtStart != null) {
            user.createdAt.goe(LocalDateTime.of(createdAtStart, LocalTime.MIN))
        } else {
            null
        }
    }

    private fun createdAtLoe(createdAtEnd: LocalDate?): BooleanExpression? {
        return if (createdAtEnd != null) {
            user.createdAt.loe(LocalDateTime.of(createdAtEnd, LocalTime.MAX).withNano(0))
        } else {
            null
        }
    }
}