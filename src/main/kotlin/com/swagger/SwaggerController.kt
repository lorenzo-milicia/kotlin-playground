package com.swagger

import com.swagger.api.ArticleApi
import com.swagger.model.Article
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class SwaggerController: ArticleApi {

	override fun articlePost(body: Article): ResponseEntity<Void> {
		body.toModel()

		return ResponseEntity.ok().build()
	}

}

