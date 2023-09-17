package com.group.blogapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BlogappApplication

fun main(args: Array<String>) {
	runApplication<BlogappApplication>(*args)
}
