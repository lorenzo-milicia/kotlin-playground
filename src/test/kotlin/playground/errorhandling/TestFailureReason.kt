package playground.errorhandling

import errorhandling.Reason
import org.springframework.http.ResponseEntity
import errorhandling.Error as MyError

sealed class TestFailureReason: Reason

object WhateverReason: TestFailureReason() {

	override val explanation: String = "It failed for whatever reason"
}

fun TestFailureReason.toHttpResponse(): ResponseEntity<MyError> =
	when (this) {
		WhateverReason -> ResponseEntity.badRequest().body(MyError())
	}
