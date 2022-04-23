package playground.errorhandling

import org.springframework.http.ResponseEntity

class TestErrorHandlingController(
	val mockRequestHandlerMethod: (String) -> errorhandling.Result<String, TestFailureReason>
) {
	fun handleRequest(request: String): ResponseEntity<String> {
		return mockRequestHandlerMethod(request)
			.onSuccess {
				ResponseEntity.ok("Everything is Okay")
			}
			.onFailure {
				when (it) {
					WhateverReason -> ResponseEntity.badRequest().body(it.explanation)
				}
			}
	}
}