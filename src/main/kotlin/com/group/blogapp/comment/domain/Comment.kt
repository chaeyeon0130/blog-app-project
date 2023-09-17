package com.group.blogapp.comment.domain

import com.group.blogapp.article.domain.Article
import com.group.blogapp.common.domain.BaseEntity
import com.group.blogapp.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "comments")
class Comment(
    @Column(name = "content", nullable = false)
    var content: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    val id: Long? = null
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    var article: Article? = null
        set(value) {
            field?.comments?.remove(this)

            field = value
            field?.comments?.add(this)
        }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
        set(value) {
            field?.comments?.remove(this)

            field = value
            field?.comments?.add(this)
        }
}