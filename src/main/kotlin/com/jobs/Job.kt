package com.jobs

import java.time.LocalDateTime
import java.util.*

class Job(
	private val repository: IJobRepository,
	execute: Job.() -> Unit,
): IJob() {

	lateinit var id: UUID
	lateinit var startedAt: LocalDateTime
	lateinit var endedAt: LocalDateTime
	var status: JobStatus = JobStatus.IN_PROGRESS

	override val tasks: MutableList<IJob.Task> = mutableListOf()

	init {
		start()
		try {
			this.execute()
			closeJob(JobStatus.OK)
		}
		catch (e: Exception) {
			println("Whoops, an exception got caught: ${e.message}")
			closeJob(JobStatus.KO)
		}
	}

	private fun start() {
		id = UUID.randomUUID()
		startedAt = LocalDateTime.now()
		println("Job started")
		repository.saveJob(this)
	}

	private fun closeJob(status: JobStatus) {
		endedAt = LocalDateTime.now()
		this.status = status
		println("Job closed with status ${status.name}")
		repository.saveJob(this)
	}

	inner class DataTask(
		execute: DataTask.() -> Unit
	): IJob.Task() {

		override lateinit var id: UUID
		override lateinit var startedAt: LocalDateTime
		override lateinit var endedAt: LocalDateTime
		override val repository: IJobRepository = this@Job.repository

		override var type: TaskEnum = TaskEnum.DATA

		init {
			start()
			try {
				execute()
				closeTask()
			}
			catch (e: Exception) {
				closeTask()
				throw e
			}
		}

		fun fetchData(): Data {
			println("Fetching data...")
			return Data()
		}

		fun processData(data: Data) {
			println("Processing data")
		}
	}

	inner class NotificationTask(
		execute: NotificationTask.() -> Unit
	): IJob.Task() {

		override lateinit var id: UUID
		override lateinit var startedAt: LocalDateTime
		override lateinit var endedAt: LocalDateTime
		override val repository: IJobRepository = this@Job.repository

		override var type: TaskEnum = TaskEnum.NOTIFICATION

		init {
			start()
			try {
				execute()
				closeTask()
			}
			catch (e: Exception) {
				closeTask()
				throw e
			}
		}

		fun sendNotifications() {
			println("Sending notifications")
		}
	}

}