package playground.errorhandling

import errorhandling.Failure
import errorhandling.Result
import errorhandling.Success
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import kotlin.test.assertEquals
import kotlin.test.assertIs

class ErrorHandlingTest {

	@Test
	internal fun `A function that returns a Success`() {
		val successFunction =
			fun(): Result<String, TestFailureReason> {
				return Success("This is a success!")
			}

		val result = successFunction()

		assertIs<Success<String>>(result)
			.also { println(result.value) }
	}

	@Test
	internal fun `A function that returns a Failure`() {
		val failureFunction =
			fun(): Result<String, TestFailureReason> {
				return Failure(WhateverReason)
			}

		val result = failureFunction()

		assertIs<Failure<TestFailureReason>>(result)
			.also { println(result.reason) }
	}

	@Test
	internal fun `A failure for whatever reason is mapped to a bad request HTTP response`() {
		val failureReason: TestFailureReason = WhateverReason

		val result = failureReason.toResponseEntity()

		assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
			.also { println(result.body) }
	}
}

