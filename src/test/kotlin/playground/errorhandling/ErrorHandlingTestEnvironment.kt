package playground.errorhandling

import errorhandling.Reason
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

sealed class TestFailureReason: Reason

object InvalidStuff: TestFailureReason() {

	override val explanation: String = "It failed for whatever reason"
}

fun Reason.toErrorResponse(status: HttpStatus): ResponseEntity<String> =
		ResponseEntity.status(status).body(explanation)

