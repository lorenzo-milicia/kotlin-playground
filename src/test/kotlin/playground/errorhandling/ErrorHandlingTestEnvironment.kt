package playground.errorhandling

import errorhandling.Reason
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

sealed class TestFailureReason: Reason

object WhateverReason: TestFailureReason() {

	override val explanation: String = "It failed for whatever reason"
}

fun TestFailureReason.toErrorResponse(status: HttpStatus): ResponseEntity<String> =
	when (this) {
		WhateverReason -> ResponseEntity.status(status).body(explanation)
	}
