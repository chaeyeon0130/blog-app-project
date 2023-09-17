package com.group.blogapp.comment.domain

import com.group.blogapp.comment.domain.QComment.comment
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

@Component
class CommentQuerydslRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findByArticleIdAndCommentId(articleId: Long, commentId: Long): Comment? {
        return queryFactory.selectFrom(comment)
            .where(
                comment.article.id.eq(articleId)
                    .and(comment.id.eq(commentId))
            )
            .fetchOne()
    }
}