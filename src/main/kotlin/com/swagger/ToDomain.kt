package com.swagger

import com.swagger.model.Article
import com.swagger.model.DiscountedArticle
import com.swagger.model.FullPriceArticle

fun Article.toModel() {
	when (this) {
		is FullPriceArticle  -> println("Damn, this costs a lot!").also { println("This article is ${this.javaClass}") }
		is DiscountedArticle -> println("What a deal!").also { println("This article is ${this.javaClass}") }
	}
}