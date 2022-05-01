package errorhandling

sealed class Result<out T, out E: Reason>

data class Success<out T>(val value: T): Result<T, Nothing>()
data class Failure<out E: Reason>(val reason: E): Result<Nothing, E>()

