package com.jobs

import java.time.LocalDateTime
import java.util.*

class Job(
	private val repository: IJobRepository,
	execute: Job.() -> Unit,
): IJob() {

	override lateinit var id: UUID
	override lateinit var startedAt: LocalDateTime
	override lateinit var endedAt: LocalDateTime
	override var status: JobStatus = JobStatus.IN_PROGRESS

	override val tasks: MutableList<IJob.Task> = mutableListOf()

	init {
		start()
		try {
			this.execute()
			close(JobStatus.OK)
		}
		catch (e: Exception) {
			println("Whoops, an exception got caught: ${e.message}")
			close(JobStatus.KO)
		}
	}

	override fun start() {
		super.start()
		repository.saveJob(this)
	}

	override fun close(status: JobStatus) {
		super.close(status)
		repository.saveJob(this)
	}

	inner class DataTask(
		override val context: DataTaskContext,
		execute: DataTask.() -> Unit
	): IJob.Task() {

		override lateinit var id: UUID
		override lateinit var startedAt: LocalDateTime
		override lateinit var endedAt: LocalDateTime

		val repository: IJobRepository = this@Job.repository

		override var type: TaskEnum = TaskEnum.DATA

		init {
			start()
			try {
				execute()
				close()
			}
			catch (e: Exception) {
				close()
				throw e
			}
		}
	}

	inner class NotificationTask(
		override val context: NotificationTaskContext,
		execute: NotificationTask.() -> Unit
	): IJob.Task() {

		override lateinit var id: UUID
		override lateinit var startedAt: LocalDateTime
		override lateinit var endedAt: LocalDateTime
		val repository: IJobRepository = this@Job.repository

		override var type: TaskEnum = TaskEnum.NOTIFICATION

		init {
			start()
			try {
				execute()
				close()
			}
			catch (e: Exception) {
				close()
				throw e
			}
		}

		fun sendNotifications() {
			println("Sending notifications")
		}
	}

}