package com.streamresponse

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody
import java.io.File
import java.io.OutputStream
import java.nio.file.Files


@RestController
class StreamController {

	@GetMapping("api/image")
	fun downloadImage(): ResponseEntity<StreamingResponseBody> {
		val stream = StreamingResponseBody {			outputStream ->
			getImage(outputStream)
		}

		return ResponseEntity.ok(stream)
	}

	private fun getImage(outputStream: OutputStream) {
		val file = File("src/main/resources/DSC_2202_DxO_1.jpg")
		Files.copy(file.toPath(), outputStream)
	}
}