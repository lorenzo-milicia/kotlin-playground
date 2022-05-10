package com.jobs

import java.time.LocalDateTime
import java.util.*

abstract class IJob {

	abstract var id: UUID
	abstract var startedAt: LocalDateTime
	abstract var endedAt: LocalDateTime
	abstract var status: JobStatus

	abstract val tasks: MutableList<Task>

	open fun start() {
		id = UUID.randomUUID()
		startedAt = LocalDateTime.now()
		println("Job started")
	}

	open fun close(status: JobStatus) {
		endedAt = LocalDateTime.now()
		this.status = status
		println("Job closed with status ${status.name}")
	}

	abstract inner class Task {
		abstract var id: UUID
		abstract var startedAt: LocalDateTime
		abstract var endedAt: LocalDateTime
		abstract var type: TaskEnum
		abstract val context: TaskContext

		open fun start() {
			id = UUID.randomUUID()
			startedAt = LocalDateTime.now()
			tasks.add(this)
			println("Task of type ${type.name} started")
		}

		open fun close() {
			endedAt = LocalDateTime.now()
			println("Task of type ${type.name} closed")
		}
	}
}