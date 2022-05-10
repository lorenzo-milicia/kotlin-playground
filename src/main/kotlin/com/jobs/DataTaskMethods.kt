package com.jobs

internal fun Job.DataTask.fetchData(): Data {
	println("Fetching data...")
	return Data()
}

internal fun Job.DataTask.processData(data: Data) {
	println("Processing data")

}