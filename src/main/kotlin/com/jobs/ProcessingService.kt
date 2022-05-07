package com.jobs

import org.springframework.stereotype.Service

@Service
class ProcessingService(
	private val jobLogRepository: IJobRepository,
) {

	fun handleData() {
		Job(jobLogRepository) {
			DataTask {
				val data = fetchData()
				processData(data)
			}
			NotificationTask {
				sendNotifications()
			}
		}
	}

}

