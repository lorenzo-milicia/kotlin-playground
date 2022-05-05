package playground.errorhandling

import com.errorhandling.Result
import com.errorhandling.onFailure
import com.errorhandling.onSuccess
import com.errorhandling.pipe
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class TestErrorHandlingController(
	val mockRequestHandlerMethod: (String) -> Result<String, TestFailureReason>,
) {

	fun handleRequest(request: String): ResponseEntity<String> =
		mockRequestHandlerMethod(request)
			.pipe {
				"$it - But with some extra stuff"
			}
			.onSuccess {
				ResponseEntity.ok(it)
			}
			.onFailure {
				when (it) {
					InvalidStuff -> it.toErrorResponse(HttpStatus.BAD_REQUEST)
				}
			}
}