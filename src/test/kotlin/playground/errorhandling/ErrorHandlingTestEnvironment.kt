package playground.errorhandling

import errorhandling.Failure
import errorhandling.Reason
import errorhandling.Success
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

typealias EitherFailureOrResponse<T, U> = Pair<Failure<T>?, ResponseEntity<U>?>
sealed class TestFailureReason: Reason

object WhateverReason: TestFailureReason() {

	override val explanation: String = "It failed for whatever reason"
}

fun TestFailureReason.toErrorResponse(status: HttpStatus): ResponseEntity<String> =
	when (this) {
		WhateverReason -> ResponseEntity.status(status).body(explanation)
	}


sealed class ProcessedResult<T, E: Reason> 

data class FailedResult<E: Reason>(
	val failure: Failure<E>
) : ProcessedResult<Nothing, E>

data class SuccessResult<T>(
	val body: T
): ProcessedResult<T, Nothing>

fun <T, E: Reason, R> errorhandling.Result<T, E>.onSuccess(map: (T) -> R): ProcessedResult<E, R> {
	return when (this) {
		is Success -> ProcessedResult(map(this))
		is Failure -> FailedResult(this)
	}
}

fun <E: Reason, R> ProcessedResult<E, R>.onFailure(map: (E) -> R): R {
	return when (this) {
		is SuccessResult -> this.body
		is FailedResult -> map(this.failure)
	}
}

fun <T, E: Reason, P> errorhandling.Result<T, E>.pipe(operation: (T) -> errorhandling.Result<P, E>): errorhandling.Result<P, E> {
	return when (this) {
		is Success -> operation(this.value)
		is Failure -> this
	}
}

