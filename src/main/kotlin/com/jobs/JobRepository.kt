package com.jobs

import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class JobRepository(
	private val dataSource: DataSource
): IJobRepository {

	override fun saveJob(job: Job) {
		println("Job saved")
	}

	override fun saveTask(task: IJob.Task) {
		println("Task saved")
	}

}

@Repository
interface IJobRepository {
	fun saveJob(job: Job)
	fun saveTask(task: IJob.Task)
}