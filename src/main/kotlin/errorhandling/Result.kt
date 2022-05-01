package errorhandling

sealed class Result<out Success, out Failure: Reason>

data class Success<out Value>(val value: Value): Result<Value, Nothing>()
data class Failure<out Fail: Reason>(val reason: Fail): Result<Nothing, Fail>()

sealed class EitherValueOrFailure<out Value, out Failure: Reason>

data class ProcessedValue<out Value>(val body: Value): EitherValueOrFailure<Value, Nothing>()

data class UnprocessedFailure<Fail: Reason>(val failure: Failure<Fail>): EitherValueOrFailure<Nothing, Fail>()


fun <Succ, Fail: Reason, T> Result<Succ, Fail>.onSuccess(map: (Succ) -> T): EitherValueOrFailure<T, Fail> {
	return when (this) {
		is Success -> ProcessedValue(map(this.value))
		is Failure -> UnprocessedFailure(this)
	}
}

fun <Value, Fail: Reason> EitherValueOrFailure<Value, Fail>.onFailure(map: (Fail) -> Value): Value {
	return when (this) {
		is ProcessedValue     -> this.body
		is UnprocessedFailure -> map(this.failure.reason)
	}
}

fun <In, Fail: Reason, Out> Result<In, Fail>.pipe(operation: (In) -> Out): Result<Out, Fail> {
	return when (this) {
		is Success -> Success(operation(this.value))
		is Failure -> this
	}
}