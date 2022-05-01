package errorhandling

sealed class Result<out T, out E: Reason>

data class Success<out T>(val value: T): Result<T, Nothing>()
data class Failure<out E: Reason>(val reason: E): Result<Nothing, E>()

sealed class EitherValueOrFailure<out R, out F: Reason>

data class ProcessedValue<out S>(val body: S): EitherValueOrFailure<S, Nothing>()

data class UnprocessedFailure<F: Reason>(val failure: Failure<F>): EitherValueOrFailure<Nothing, F>()


fun <S, F: Reason, R> Result<S, F>.onSuccess(map: (S) -> R): EitherValueOrFailure<R, F> {
	return when (this) {
		is Success -> ProcessedValue(map(this.value))
		is Failure -> UnprocessedFailure(this)
	}
}

fun <R, F: Reason> EitherValueOrFailure<R, F>.onFailure(map: (F) -> R): R {
	return when (this) {
		is ProcessedValue     -> this.body
		is UnprocessedFailure -> map(this.failure.reason)
	}
}

fun <S, F: Reason, P> Result<S, F>.pipe(operation: (S) -> P): Result<P, F> {
	return when (this) {
		is Success -> Success(operation(this.value))
		is Failure -> this
	}
}