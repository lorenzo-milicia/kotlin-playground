package com.jobs

import org.springframework.stereotype.Service

@Service
class ProcessingService(
	private val jobLogRepository: IJobRepository,
) {

	fun handleData() {
		Job(jobLogRepository) {
			DataTask(DataTaskContext()) {
				val data = fetchData()
				processData(data)
			}
			NotificationTask(NotificationTaskContext()) {
				sendNotifications()
			}
		}
	}

}

