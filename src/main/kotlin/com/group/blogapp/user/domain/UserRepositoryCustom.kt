package com.group.blogapp.user.domain

import com.group.blogapp.user.dto.request.UserSearchCondition
import com.group.blogapp.user.dto.response.UserSearchResponse

interface UserRepositoryCustom {
    fun searchByWhere(condition: UserSearchCondition): List<UserSearchResponse>
}