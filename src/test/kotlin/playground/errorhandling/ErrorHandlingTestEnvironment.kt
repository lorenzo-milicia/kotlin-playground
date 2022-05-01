package playground.errorhandling

import errorhandling.Failure
import errorhandling.Reason
import errorhandling.Success
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


sealed class ProcessedResult<out R, out F: Reason>

data class SuccessResult<out S>(val body: S): ProcessedResult<S, Nothing>()

data class FailedResult<F: Reason>(val failure: Failure<F>): ProcessedResult<Nothing, F>()

fun <S, F: Reason, R> errorhandling.Result<S, F>.onSuccess(map: (S) -> R): ProcessedResult<R, F> {
	return when (this) {
		is Success -> SuccessResult(map(this.value))
		is Failure -> FailedResult(this)
	}
}

fun <R, F: Reason> ProcessedResult<R, F>.onFailure(map: (F) -> R): R {
	return when (this) {
		is SuccessResult -> this.body
		is FailedResult  -> map(this.failure.reason)
	}
}

fun <S, F: Reason, P> errorhandling.Result<S, F>.pipe(operation: (S) -> P): errorhandling.Result<P, F> {
	return when (this) {
		is Success -> Success(operation(this.value))
		is Failure -> this
	}
}

