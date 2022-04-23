package playground.errorhandling

import errorhandling.Failure
import errorhandling.Reason
import errorhandling.Success
import org.springframework.http.ResponseEntity

typealias EitherFailureOrResponse<T, U> = Pair<Failure<T>?, ResponseEntity<U>?>
sealed class TestFailureReason: Reason

object WhateverReason: TestFailureReason() {

	override val explanation: String = "It failed for whatever reason"
}

fun TestFailureReason.toResponseEntity(): ResponseEntity<String> =
	when (this) {
		WhateverReason -> ResponseEntity.badRequest().body(explanation)
	}


fun <T, E: Reason, R> errorhandling.Result<T, E>.onSuccess(map: (T) -> ResponseEntity<R>): EitherFailureOrResponse<E, R> {
	return when (this) {
		is Success -> Pair(null, map(this.value))
		is Failure -> Pair(this, null)
	}
}

fun <E: Reason, R> EitherFailureOrResponse<E, R>.onFailure(map: (E) -> ResponseEntity<R>): ResponseEntity<R> {
	return first?.let{map(it.reason)} ?: second!!
}

