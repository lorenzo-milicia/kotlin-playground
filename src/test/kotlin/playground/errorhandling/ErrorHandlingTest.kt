package playground.errorhandling

import com.errorhandling.Failure
import com.errorhandling.Result
import com.errorhandling.Success
import com.errorhandling.pipe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
				return Failure(InvalidStuff)
			}

		val result = failureFunction()

		assertIs<Failure<TestFailureReason>>(result)
			.also { println(result.reason.explanation) }
	}

	@Test
	internal fun `A success is mapped to an ok status response`() {
		val requestHandler =
			fun(_: String): Result<String, TestFailureReason> {
				return Success("This test was a success!")
			}

		val controller = TestErrorHandlingController(requestHandler)

		val response = controller.handleRequest("Test Request")

		assertEquals(HttpStatus.OK, response.statusCode).also { println(response.body) }
	}

	@Test
	internal fun `A failure for whatever reason is mapped to a bad request HTTP response`() {
		val requestHandler =
			fun(_: String): Result<String, TestFailureReason> {
				return Failure(InvalidStuff)
			}

		val controller = TestErrorHandlingController(requestHandler)

		val response = controller.handleRequest("Test Request")

		assertEquals(HttpStatus.BAD_REQUEST, response.statusCode).also { println(response.body) }
	}

	@Test
	internal fun `Piping a Success`() {
		val success = Success("This is a success!")

		val result =
			success.pipe { ResponseEntity.ok("$it And now it got piped to something else.") }

		assertIs<Success<ResponseEntity<String>>>(result)
	}

	@Test
	internal fun `Piping a Failure`() {
		val failure = Failure(InvalidStuff)

		val result =
			failure.pipe { ResponseEntity.ok("This should never get here") }

		assertIs<Failure<TestFailureReason>>(result)
		assertEquals(InvalidStuff, result.reason)
	}
}

