package com.group.blogapp.article.domain

import com.group.blogapp.comment.domain.Comment
import com.group.blogapp.common.domain.BaseEntity
import com.group.blogapp.user.domain.User
import javax.persistence.*

@Entity
@Table(name = "articles")
class Article(
    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @OneToMany(mappedBy = "article", cascade = [CascadeType.ALL], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    val id: Long? = null
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null
        set(value) {
            field?.articles?.remove(this)

            field = value
            field?.articles?.add(this)
        }
}