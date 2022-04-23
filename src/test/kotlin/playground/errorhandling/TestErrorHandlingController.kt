package playground.errorhandling

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class TestErrorHandlingController(
	val mockRequestHandlerMethod: (String) -> errorhandling.Result<String, TestFailureReason>,
) {

	fun handleRequest(request: String): ResponseEntity<String> =
		mockRequestHandlerMethod(request)
			.onSuccess {
				ResponseEntity.ok(it)
			}
			.onFailure {
				when (it) {
					WhateverReason -> it.toErrorResponse(HttpStatus.BAD_REQUEST)
				}
			}
}