package playground.errorhandling

import errorhandling.onFailure
import errorhandling.onSuccess
import errorhandling.pipe
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class TestErrorHandlingController(
	val mockRequestHandlerMethod: (String) -> errorhandling.Result<String, TestFailureReason>,
) {

	fun handleRequest(request: String): ResponseEntity<String> =
		mockRequestHandlerMethod(request)
			.pipe {
				"$it - But with some extra stuff"
			}
			.onSuccess {
				ResponseEntity.ok(it)
			}
			.onFailure {
				when (it) {
					WhateverReason -> it.toErrorResponse(HttpStatus.BAD_REQUEST)
				}
			}
}