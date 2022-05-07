package com.jobs

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class ProcessingServiceTest {

	private val repository: IJobRepository = mockk()

	private val service: ProcessingService = ProcessingService(repository)


	@Test
	internal fun Testing() {

		every { repository.saveJob(any()) } just Runs
		every { repository.saveTask(any()) } just Runs

		service.handleData()
	}
}