package com.jobs

import java.time.LocalDateTime
import java.util.*

abstract class IJob {

	abstract val tasks: MutableList<Task>

	abstract inner class Task {
		abstract var id: UUID
		abstract var startedAt: LocalDateTime
		abstract var endedAt: LocalDateTime
		abstract var type: TaskEnum
		abstract val repository: IJobRepository

		fun start() {
			id = UUID.randomUUID()
			startedAt = LocalDateTime.now()
			repository.saveTask(this)
			tasks.add(this)
			println("Task of type ${type.name} started")
		}

		fun closeTask() {
			endedAt = LocalDateTime.now()
			println("Task of type ${type.name} closed")
			repository.saveTask(this)
		}
	}
}